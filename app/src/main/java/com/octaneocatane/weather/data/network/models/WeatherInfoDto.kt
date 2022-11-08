package com.octaneocatane.weather.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.octaneocatane.weather.data.network.models.CurrentDto
import com.octaneocatane.weather.data.network.models.ForecastDto
import com.octaneocatane.weather.data.network.models.LocationDto

data class WeatherInfoDto(

    @SerializedName("location")
    @Expose
    val location: LocationDto,

    @SerializedName("current")
    @Expose
    val current: CurrentDto,

    @SerializedName("forecast")
    @Expose
    val forecast: ForecastDto

)