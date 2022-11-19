package com.octaneocatane.weather.presentation.fragments

import com.octaneocatane.domain.entity.WeatherEntity

interface DataFragmentsInterface {
    fun setDataToViewModel(data: WeatherEntity)
}