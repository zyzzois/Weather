package com.octaneocatane.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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