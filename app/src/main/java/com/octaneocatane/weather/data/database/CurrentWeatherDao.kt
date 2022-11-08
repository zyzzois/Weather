package com.octaneocatane.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.octaneocatane.weather.data.database.modelsDB.CurrentModelDB

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeather(): CurrentModelDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentModelDB: CurrentModelDB)

    @Query("DELETE FROM current_weather")
    suspend fun clearCurrentWeather()
}