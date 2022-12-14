package com.octaneocatane.domain.entity

data class WeatherEntity(
    val time: String,
    val city: String,
    val conditionText: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val conditionIcon: String,
    val lastUpdated: String
)