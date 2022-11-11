package com.octaneocatane.weather.domain.usecases

import com.octaneocatane.weather.domain.WeatherRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(city: String) {
        repository.loadData(city)
    }

}