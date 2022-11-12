package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.HourItemModelDB
import com.octaneocatane.weather.data.mapper.DaysListMapper.Companion.DEGREE_SYMBOL
import com.octaneocatane.weather.data.network.models.HourDto
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HoursListMapper @Inject constructor(){

    fun mapDtoToHoursListModelDb(weatherInfoDto: WeatherInfoDto) =
        weatherInfoDto.forecast.forecastForDaysList[0].forecastForHoursList.map {
            parseHour(it)
        }

    private fun parseHour(hour: HourDto) = HourItemModelDB(
        id = hour.idHour,
        time = hour.time,
        conditionText = hour.condition.conditionText,
        conditionIcon = hour.condition.conditionIcon,
        temp = hour.tempC.toInt()
    )

    private fun mapHoursModelDbToEntity(hourItem: HourItemModelDB) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = convertDateToHour(hourItem.time),
        conditionText = hourItem.conditionText,
        currentTemp = hourItem.temp.toString() + DEGREE_SYMBOL,
        maxTemp = Constants.UNDEFINED_MAX_TEMP,
        minTemp = Constants.UNDEFINED_MIN_TEMP,
        conditionIcon = hourItem.conditionIcon
    )

    fun mapHourModelDbListToEntityList(modelDbList: List<HourItemModelDB>): List<WeatherEntity> {
        return modelDbList.map {
            mapHoursModelDbToEntity(it)
        }
    }

    private fun convertDateToHour(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val hhMmFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val result = inputFormat.parse(date)
        return hhMmFormat.format(result)
    }

}