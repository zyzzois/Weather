package com.octaneocatane.data.mapper

import com.octaneocatane.data.database.modelsDB.CurrentModelDB
import com.octaneocatane.data.network.models.WeatherInfoDto
import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.utils.Constants.BASE_URL
import com.octaneocatane.utils.Constants.DAY_PATTERN
import com.octaneocatane.utils.Constants.DEFAULT_HOUR
import com.octaneocatane.utils.Constants.DEGREE_SYMBOL
import com.octaneocatane.utils.Constants.FULL_TIME_PATTERN
import com.octaneocatane.utils.Constants.HOURS_MINUTES_PATTERN
import com.octaneocatane.utils.Constants.MONTHS_PATTERN
import com.octaneocatane.utils.Constants.MONTH_LIST
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
        conditionIcon = BASE_URL + weatherInfoDto.current.condition.conditionIcon,
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
        lastUpdated = DEFAULT_HOUR
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
        return "$day ${MONTH_LIST[monthNumber.toInt() - 1]} $time"
    }

}