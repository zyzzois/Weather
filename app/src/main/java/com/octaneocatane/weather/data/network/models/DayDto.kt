package com.octaneocatane.weather.data.network.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class DayDto (

    @SerializedName("maxtemp_c")
    @Expose
    val maxTemp: Double,

    @SerializedName("mintemp_c")
    @Expose
    val minTemp: Double,

    @SerializedName("avgtemp_c")
    @Expose
    val averageTemp: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto,

    )