package com.octaneocatane.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.octaneocatane.weather.data.database.modelsDB.HourItemModelDB

@Dao
interface HoursWeatherDao {

    @Query("SELECT * FROM hours_list")
    suspend fun getHoursWeatherList(): List<HourItemModelDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoursWeatherList(hourList: List<HourItemModelDB>)

    @Query("DELETE FROM hours_list")
    suspend fun clearHoursWeather()
}