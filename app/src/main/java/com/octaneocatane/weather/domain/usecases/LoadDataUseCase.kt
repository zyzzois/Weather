package com.octaneocatane.weather.domain.usecases

import com.octaneocatane.weather.domain.WeatherRepository

class LoadDataUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(city: String) {
        repository.loadData(city)
    }

}