package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.domain.repository.WeatherRepository
import javax.inject.Inject

class GetHoursListUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return repository.getHoursWeatherList()
    }
}