package com.octaneocatane.data.mapper

import com.octaneocatane.data.database.modelsDB.DayItemModelDb
import com.octaneocatane.data.network.models.ForecastdayDto
import com.octaneocatane.data.network.models.WeatherInfoDto
import com.octaneocatane.domain.WeatherEntity
import com.octaneocatane.utils.Constants.DAY_PATTERN
import com.octaneocatane.utils.Constants.DEFAULT_HOUR
import com.octaneocatane.utils.Constants.DEGREE_SYMBOL
import com.octaneocatane.utils.Constants.MONTHS_PATTERN
import com.octaneocatane.utils.Constants.MONTH_LIST
import com.octaneocatane.utils.Constants.PART_TIME_PATTERN
import com.octaneocatane.utils.Constants.UNDEFINED_CITY
import com.octaneocatane.utils.Constants.UNDEFINED_CURRENT_TEMP
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
        city = UNDEFINED_CITY,
        time = day.date,
        conditionText = day.day.condition.conditionText,
        conditionIcon = day.day.condition.conditionIcon,
        maxTemp = day.day.maxTemp.toInt(),
        minTemp = day.day.minTemp.toInt(),
        temp = day.day.averageTemp.toInt()
    )

    private fun mapDaysModelDbToEntity(dayItem: DayItemModelDb) = WeatherEntity(
        city = UNDEFINED_CITY,
        time = convertDate(dayItem.time),
        conditionText = dayItem.conditionText,
        currentTemp = UNDEFINED_CURRENT_TEMP,
        maxTemp = dayItem.maxTemp.toString() + DEGREE_SYMBOL,
        minTemp = dayItem.minTemp.toString() + DEGREE_SYMBOL,
        conditionIcon = dayItem.conditionIcon,
        lastUpdated = DEFAULT_HOUR
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
        return "$day ${MONTH_LIST[monthNumber.toInt() - 1]} "
    }

}