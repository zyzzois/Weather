package com.octaneocatane.weather.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.octaneocatane.weather.data.network.models.ForecastdayDto

data class ForecastDto (

    @SerializedName("forecastday")
    @Expose
    val forecastForDaysList: List<ForecastdayDto>

)