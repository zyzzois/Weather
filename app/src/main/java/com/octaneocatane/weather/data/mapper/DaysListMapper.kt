package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.DayItemModelDb
import com.octaneocatane.weather.utils.Constants
import com.octaneocatane.weather.data.network.models.ForecastdayDto
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity
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
    fun mapDaysModelDbToEntity(dayItem: DayItemModelDb) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = dayItem.time,
        conditionText = dayItem.conditionText,
        currentTemp = Constants.UNDEFINED_CURRENT_TEMP,
        maxTemp = dayItem.maxTemp.toString() + "°",
        minTemp = dayItem.minTemp.toString() + "°",
        conditionIcon = dayItem.conditionIcon
    )
    fun mapDaysModelDbListToEntityList(modelDbList: List<DayItemModelDb>): List<WeatherEntity>{
       return modelDbList.map {
            mapDaysModelDbToEntity(it)
        }
    }
}