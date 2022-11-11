package com.octaneocatane.weather.di

import android.app.Application
import com.octaneocatane.weather.data.database.AppDatabase
import com.octaneocatane.weather.data.database.CurrentWeatherDao
import com.octaneocatane.weather.data.database.DaysWeatherDao
import com.octaneocatane.weather.data.database.HoursWeatherDao
import com.octaneocatane.weather.data.network.ApiFactory
import com.octaneocatane.weather.data.network.ApiService
import com.octaneocatane.weather.data.repository.WeatherRepositoryImpl
import com.octaneocatane.weather.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule  {

    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCurrentWeatherDao(
            application: Application
        ): CurrentWeatherDao {
            return AppDatabase.getInstance(application).currentWeatherDao()
        }

        @Provides
        @ApplicationScope
        fun provideDaysWeatherDao(
            application: Application
        ): DaysWeatherDao {
            return AppDatabase.getInstance(application).daysWeatherDao()
        }

        @Provides
        @ApplicationScope
        fun provideHoursWeatherDao(
            application: Application
        ): HoursWeatherDao {
            return AppDatabase.getInstance(application).hoursWeatherDao()
        }

        @Provides
        @ApplicationScope
        fun provideOpenWeatherApi(): ApiService {
            return ApiFactory.apiService
        }

    }

}