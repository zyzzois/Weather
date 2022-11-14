package com.octaneocatane.data.database.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentModelDB(
    @PrimaryKey
    val time: String,
    val city: String,
    val conditionText: String,
    val conditionIcon: String,
    val currentTemp: Int,
    val maxTemp: Int,
    val minTemp: Int
)