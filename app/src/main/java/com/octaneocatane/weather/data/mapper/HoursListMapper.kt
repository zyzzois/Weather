package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.HourItemModelDB
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.FULL_TIME_PATTERN
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.HOURS_MINUTES_PATTERN
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.HOURS_PATTERN
import com.octaneocatane.weather.data.network.models.HourDto
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants
import com.octaneocatane.weather.utils.Constants.DEGREE_SYMBOL
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
        conditionIcon = hour.condition.conditionIcon,
        temp = hour.tempC.toInt(),
        lastUpdatedHour = time
    )

    private fun mapHoursModelDbToEntity(hourItem: HourItemModelDB) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = convertDateToHour(hourItem.time),
        conditionText = hourItem.conditionText,
        currentTemp = hourItem.temp.toString() + DEGREE_SYMBOL,
        maxTemp = Constants.UNDEFINED_MAX_TEMP,
        minTemp = Constants.UNDEFINED_MIN_TEMP,
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