package com.octaneocatane.weather.domain.usecases

import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.WeatherRepository
import javax.inject.Inject

class GetDaysListUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return weatherRepository.getDaysWeatherList()
    }
}