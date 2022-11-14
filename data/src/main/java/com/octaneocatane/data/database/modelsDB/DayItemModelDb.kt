package com.octaneocatane.data.database.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days_list")
data class DayItemModelDb (
    @PrimaryKey
    val id: Int,
    val minTemp: Int,
    val city: String,
    val time: String,
    val conditionText: String,
    val conditionIcon: String,
    val maxTemp: Int,
    val temp: Int,

)