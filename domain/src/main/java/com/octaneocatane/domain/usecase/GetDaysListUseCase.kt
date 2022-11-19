package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDaysListUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return weatherRepository.getDaysWeatherList()
    }
}