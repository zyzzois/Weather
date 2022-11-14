package com.octaneocatane.weather.di

import android.app.Application
import com.octaneocatane.data.database.AppDatabase
import com.octaneocatane.data.database.CurrentWeatherDao
import com.octaneocatane.data.database.DaysWeatherDao
import com.octaneocatane.data.database.HoursWeatherDao
import com.octaneocatane.data.network.ApiFactory
import com.octaneocatane.data.network.ApiService
import com.octaneocatane.data.repository.WeatherRepositoryImpl
import com.octaneocatane.domain.WeatherRepository
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