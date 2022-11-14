package com.octaneocatane.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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