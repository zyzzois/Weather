package com.octaneocatane.weather.data.mapper

import com.octaneocatane.weather.data.database.modelsDB.CurrentModelDB
import com.octaneocatane.weather.data.network.models.WeatherInfoDto
import com.octaneocatane.weather.domain.WeatherEntity

class CurrentWeatherMapper {

    fun mapDtoToCurrentDbModel(weatherInfoDto: WeatherInfoDto) = CurrentModelDB(
        time = weatherInfoDto.current.lastUpdated,
        city = weatherInfoDto.location.city,
        conditionText = weatherInfoDto.current.condition.conditionText,
        conditionIcon = weatherInfoDto.current.condition.conditionIcon,
        currentTemp = weatherInfoDto.current.tempC.toString(),
        maxTemp = weatherInfoDto.forecast.forecastForDaysList[0].day.maxTemp.toString(),
        minTemp = weatherInfoDto.forecast.forecastForDaysList[0].day.minTemp.toString()
    )

    fun mapCurrentDbModelToEntity(currentDbModel: CurrentModelDB) = WeatherEntity(
        time = currentDbModel.time,
        conditionText = currentDbModel.conditionText,
        currentTemp = currentDbModel.currentTemp,
        maxTemp = currentDbModel.maxTemp,
        minTemp = currentDbModel.minTemp,
        conditionIcon = currentDbModel.conditionIcon,
        city = currentDbModel.city
    )

}