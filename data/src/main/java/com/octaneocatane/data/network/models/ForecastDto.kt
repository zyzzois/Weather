package com.octaneocatane.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastDto (

    @SerializedName("forecastday")
    @Expose
    val forecastForDaysList: List<ForecastdayDto>

)