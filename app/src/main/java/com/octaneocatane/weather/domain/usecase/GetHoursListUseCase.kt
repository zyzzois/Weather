package com.octaneocatane.weather.domain.usecase

import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.WeatherRepository
import javax.inject.Inject

class GetHoursListUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return repository.getHoursWeatherList()
    }
}