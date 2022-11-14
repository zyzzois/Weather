package com.octaneocatane.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.octaneocatane.data.database.modelsDB.DayItemModelDb

@Dao
interface DaysWeatherDao {

    @Query("SELECT * FROM days_list")
    suspend fun getDaysWeatherList(): List<DayItemModelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaysWeatherList(dayList: List<DayItemModelDb>)

    @Query("DELETE FROM days_list")
    suspend fun clearDaysWeather()
}