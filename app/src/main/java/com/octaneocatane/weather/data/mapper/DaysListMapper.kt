package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.DayItemModelDb
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.DAY_PATTERN
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.MONTHS_PATTERN
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper.Companion.PART_TIME_PATTERN
import com.octaneocatane.weather.utils.Constants
import com.octaneocatane.weather.data.network.models.ForecastdayDto
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants.DEGREE_SYMBOL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DaysListMapper @Inject constructor(){

    fun mapDtoToDaysListModelDb(weatherInfoDto: WeatherInfoDto) =
        weatherInfoDto.forecast.forecastForDaysList.map {
            parseDay(it)
        }
    private fun parseDay(day: ForecastdayDto) = DayItemModelDb(
        id = day.idDay,
        city = Constants.UNDEFINED_CITY,
        time = day.date,
        conditionText = day.day.condition.conditionText,
        conditionIcon = day.day.condition.conditionIcon,
        maxTemp = day.day.maxTemp.toInt(),
        minTemp = day.day.minTemp.toInt(),
        temp = day.day.averageTemp.toInt()
    )
    private fun mapDaysModelDbToEntity(dayItem: DayItemModelDb) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = convertDate(dayItem.time),
        conditionText = dayItem.conditionText,
        currentTemp = Constants.UNDEFINED_CURRENT_TEMP,
        maxTemp = dayItem.maxTemp.toString() + DEGREE_SYMBOL,
        minTemp = dayItem.minTemp.toString() + DEGREE_SYMBOL,
        conditionIcon = dayItem.conditionIcon,
        lastUpdated = Constants.DEFAULT_HOUR
    )
    fun mapDaysModelDbListToEntityList(modelDbList: List<DayItemModelDb>): List<WeatherEntity>{
       return modelDbList.map {
            mapDaysModelDbToEntity(it)
        }
    }

    private fun convertDate(date: String): String {
        val inputFormat = SimpleDateFormat(PART_TIME_PATTERN, Locale.getDefault())
        val dayFormat = SimpleDateFormat(DAY_PATTERN, Locale.getDefault())
        val monthFormat = SimpleDateFormat(MONTHS_PATTERN, Locale.getDefault())
        val result = inputFormat.parse(date)!!
        val monthNumber = monthFormat.format(result)
        val day = dayFormat.format(result)
        return "$day ${Constants.MONTH_LIST[monthNumber.toInt() - 1]} "
    }

}