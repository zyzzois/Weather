package com.octaneocatane.weather.presentation.fragments

import com.octaneocatane.weather.domain.WeatherEntity

interface DataFragmentsInterface {
    fun setDataToViewModel(data: WeatherEntity)
}