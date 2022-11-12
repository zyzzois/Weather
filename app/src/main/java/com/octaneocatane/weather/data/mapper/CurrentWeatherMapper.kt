package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.CurrentModelDB
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants
import com.octaneocatane.weather.utils.Constants.DEGREE_SYMBOL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.floor

class CurrentWeatherMapper @Inject constructor(){

    fun mapDtoToCurrentDbModel(weatherInfoDto: WeatherInfoDto) = CurrentModelDB(
        time = weatherInfoDto.current.lastUpdated,
        city = weatherInfoDto.location.city,
        conditionText = weatherInfoDto.current.condition.conditionText,
        conditionIcon = weatherInfoDto.current.condition.conditionIcon,
        currentTemp = floor(weatherInfoDto.current.tempC).toInt(),
        maxTemp = ceil(weatherInfoDto.forecast.forecastForDaysList[0].day.maxTemp).toInt(),
        minTemp = weatherInfoDto.forecast.forecastForDaysList[0].day.minTemp.toInt()
    )

    fun mapCurrentDbModelToEntity(currentDbModel: CurrentModelDB) = WeatherEntity(
        time = convertDate(currentDbModel.time),
        conditionText = currentDbModel.conditionText,
        currentTemp = currentDbModel.currentTemp.toString() + DEGREE_SYMBOL,
        maxTemp = currentDbModel.maxTemp.toString() + DEGREE_SYMBOL,
        minTemp = currentDbModel.minTemp.toString() + DEGREE_SYMBOL,
        conditionIcon = currentDbModel.conditionIcon,
        city = currentDbModel.city,
        lastUpdated = Constants.DEFAULT_HOUR
    )

    private fun convertDate(date: String): String {
        val inputFormat = SimpleDateFormat(FULL_TIME_PATTERN, Locale.getDefault())
        val monthFormat = SimpleDateFormat(MONTHS_PATTERN, Locale.getDefault())
        val dayFormat = SimpleDateFormat(DAY_PATTERN, Locale.getDefault())
        val hhMmFormat = SimpleDateFormat(HOURS_MINUTES_PATTERN, Locale.getDefault())
        val result = inputFormat.parse(date)!!
        val monthNumber = monthFormat.format(result)
        val time = hhMmFormat.format(result)
        val day = dayFormat.format(result)
        return "$day ${Constants.MONTH_LIST[monthNumber.toInt() - 1]} $time"
    }


    companion object {
        const val FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm"
        const val MONTHS_PATTERN = "MM"
        const val DAY_PATTERN = "dd"
        const val HOURS_MINUTES_PATTERN = "HH:mm"
        const val HOURS_PATTERN = "HH"
        const val PART_TIME_PATTERN = "yyyy-MM-dd"
    }

}