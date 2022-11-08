package com.octaneocatane.weather.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastdayDto (

    @SerializedName("date_epoch")
    @Expose
    val idDay: Int,

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("day")
    @Expose
    val day: DayDto,

    @SerializedName("hour")
    @Expose
    val forecastForHoursList: List<HourDto>
)