package com.octaneocatane.weather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.octaneocatane.weather.data.database.modelsDB.CurrentModelDB
import com.octaneocatane.weather.data.database.modelsDB.DayItemModelDb
import com.octaneocatane.weather.data.database.modelsDB.HourItemModelDB

@Database(
    entities = [
        CurrentModelDB::class,
        DayItemModelDb::class,
        HourItemModelDB::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hoursWeatherDao(): HoursWeatherDao
    abstract fun daysWeatherDao(): DaysWeatherDao
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                db = instance
                return instance
            }
        }
    }

}
