package com.octaneocatane.weather.presentation.fragments

import com.octaneocatane.domain.WeatherEntity

interface DataFragmentsInterface {
    fun setDataToViewModel(data: WeatherEntity)
}