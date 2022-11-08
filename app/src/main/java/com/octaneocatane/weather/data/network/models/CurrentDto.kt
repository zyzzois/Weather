package com.octaneocatane.weather.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.octaneocatane.weather.data.network.models.ConditionDto

data class CurrentDto (

    @SerializedName("last_updated")
    @Expose
    val lastUpdated: String,

    @SerializedName("temp_c")
    @Expose
    val tempC: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto

)