package com.octaneocatane.weather.domain.usecases

import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.WeatherRepository

class GetCurrentWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): WeatherEntity {
        return repository.getCurrentWeather()
    }
}