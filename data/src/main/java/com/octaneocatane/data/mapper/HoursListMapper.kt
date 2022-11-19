package com.octaneocatane.data.mapper

import com.octaneocatane.data.database.modelsDB.HourItemModelDB
import com.octaneocatane.data.network.models.HourDto
import com.octaneocatane.data.network.models.WeatherInfoDto
import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.utils.Constants.BASE_URL
import com.octaneocatane.utils.Constants.DEGREE_SYMBOL
import com.octaneocatane.utils.Constants.FULL_TIME_PATTERN
import com.octaneocatane.utils.Constants.HOURS_MINUTES_PATTERN
import com.octaneocatane.utils.Constants.HOURS_PATTERN
import com.octaneocatane.utils.Constants.UNDEFINED_CITY
import com.octaneocatane.utils.Constants.UNDEFINED_MAX_TEMP
import com.octaneocatane.utils.Constants.UNDEFINED_MIN_TEMP
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HoursListMapper @Inject constructor(){

    fun mapDtoToHoursListModelDb(weatherInfoDto: WeatherInfoDto): List<HourItemModelDB> {
        val lastUpdatedHour = getCurrentHour(weatherInfoDto.current.lastUpdated)
        return weatherInfoDto.forecast.forecastForDaysList[0].forecastForHoursList.map {
            parseHour(it, lastUpdatedHour)
        }
    }

    private fun parseHour(hour: HourDto, time: String) = HourItemModelDB(
        id = hour.idHour,
        time = hour.time,
        conditionText = hour.condition.conditionText,
        conditionIcon = BASE_URL + hour.condition.conditionIcon,
        temp = hour.tempC.toInt(),
        lastUpdatedHour = time
    )

    private fun mapHoursModelDbToEntity(hourItem: HourItemModelDB) = WeatherEntity(
        city = UNDEFINED_CITY,
        time = convertDateToHour(hourItem.time),
        conditionText = hourItem.conditionText,
        currentTemp = hourItem.temp.toString() + DEGREE_SYMBOL,
        maxTemp = UNDEFINED_MAX_TEMP,
        minTemp = UNDEFINED_MIN_TEMP,
        conditionIcon = hourItem.conditionIcon,
        lastUpdated = hourItem.lastUpdatedHour
    )

    fun mapHourModelDbListToEntityList(modelDbList: List<HourItemModelDB>): List<WeatherEntity> {
        val list = modelDbList.map {
            mapHoursModelDbToEntity(it)
        }
        return sortHourListByCurrentHour(list)
    }

    private fun convertDateToHour(date: String): String {
        val inputFormat = SimpleDateFormat(FULL_TIME_PATTERN, Locale.getDefault())
        val hhMmFormat = SimpleDateFormat(HOURS_MINUTES_PATTERN, Locale.getDefault())
        val result = inputFormat.parse(date)!!
        return hhMmFormat.format(result)
    }

    private fun getCurrentHour(date: String): String {
        val inputFormat = SimpleDateFormat(FULL_TIME_PATTERN, Locale.getDefault())
        val hourFormat = SimpleDateFormat(HOURS_PATTERN, Locale.getDefault())
        return hourFormat.format(inputFormat.parse(date)!!)
    }

    private fun sortHourListByCurrentHour(list: List<WeatherEntity>): List<WeatherEntity> {
        val resultList = mutableListOf<WeatherEntity>()
        val current = list[0].lastUpdated.toInt()
        for (next in current until 24)
            resultList.add(list[next])
        for (previous in 0 until current)
            resultList.add(list[previous])
        return resultList
    }

}