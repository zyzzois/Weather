package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.CurrentModelDB
import com.octaneocatane.weather.data.mapper.DaysListMapper.Companion.DEGREE_SYMBOL
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor(){

    fun mapDtoToCurrentDbModel(weatherInfoDto: WeatherInfoDto) = CurrentModelDB(
        time = weatherInfoDto.current.lastUpdated,
        city = weatherInfoDto.location.city,
        conditionText = weatherInfoDto.current.condition.conditionText,
        conditionIcon = weatherInfoDto.current.condition.conditionIcon,
        currentTemp = weatherInfoDto.current.tempC.toInt(),
        maxTemp = weatherInfoDto.forecast.forecastForDaysList[0].day.maxTemp.toInt(),
        minTemp = weatherInfoDto.forecast.forecastForDaysList[0].day.minTemp.toInt()
    )

    fun mapCurrentDbModelToEntity(currentDbModel: CurrentModelDB) = WeatherEntity(
        time = convertDate(currentDbModel.time),
        conditionText = currentDbModel.conditionText,
        currentTemp = currentDbModel.currentTemp.toString() + DEGREE_SYMBOL,
        maxTemp = currentDbModel.maxTemp.toString() + DEGREE_SYMBOL,
        minTemp = currentDbModel.minTemp.toString() + DEGREE_SYMBOL,
        conditionIcon = currentDbModel.conditionIcon,
        city = currentDbModel.city
    )

    private fun convertDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MM", Locale.getDefault())
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val hhMmFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val result = inputFormat.parse(date)
        val monthNumber = monthFormat.format(result)
        val time = hhMmFormat.format(result)
        val day = dayFormat.format(result)
        return "$day ${monthsList[monthNumber.toInt() - 1]} $time"
    }

    private val monthsList = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

}