package com.octaneocatane.domain.usecase

import com.octaneocatane.domain.repository.WeatherRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(city: String) {
        repository.loadData(city)
    }

}