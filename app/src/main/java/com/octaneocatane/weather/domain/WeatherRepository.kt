package com.octaneocatane.weather.domain

interface WeatherRepository {

    suspend fun loadData(city: String)

    suspend fun getHoursWeatherList(): List<WeatherEntity>

    suspend fun getDaysWeatherList(): List<WeatherEntity>

    suspend fun getCurrentWeather(): WeatherEntity

}