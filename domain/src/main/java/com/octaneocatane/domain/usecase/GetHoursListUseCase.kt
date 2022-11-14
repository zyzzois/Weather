package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.WeatherEntity
import com.octaneocatane.domain.WeatherRepository
import javax.inject.Inject

class GetHoursListUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return repository.getHoursWeatherList()
    }
}