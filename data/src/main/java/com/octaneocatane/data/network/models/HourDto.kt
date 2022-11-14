package com.octaneocatane.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourDto (

    @SerializedName("time_epoch")
    @Expose
    val idHour: Int,

    @SerializedName("time")
    @Expose
    val time: String,

    @SerializedName("temp_c")
    @Expose
    val tempC: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto,

    )