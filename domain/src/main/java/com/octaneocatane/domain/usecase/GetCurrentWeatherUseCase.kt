package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): WeatherEntity {
        return repository.getCurrentWeather()
    }
}