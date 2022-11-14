package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.WeatherEntity
import com.octaneocatane.domain.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): WeatherEntity {
        return repository.getCurrentWeather()
    }
}