package com.octaneocatane.weather.domain.usecases

import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.WeatherRepository

class GetHoursListUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return repository.getHoursWeatherList()
    }
}