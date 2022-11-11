package com.octaneocatane.weather.data.repository

import com.octaneocatane.weather.data.database.CurrentWeatherDao
import com.octaneocatane.weather.data.database.DaysWeatherDao
import com.octaneocatane.weather.data.database.HoursWeatherDao
import com.octaneocatane.weather.data.mapper.CurrentWeatherMapper
import com.octaneocatane.weather.data.mapper.DaysListMapper
import com.octaneocatane.weather.data.mapper.HoursListMapper
import com.octaneocatane.weather.data.network.ApiFactory
import com.octaneocatane.weather.data.network.ApiService
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val hoursMapper: HoursListMapper,
    private val daysMapper: DaysListMapper,
    private val currentMapper: CurrentWeatherMapper,
    private val currentWeatherDao: CurrentWeatherDao,
    private val daysWeatherDao: DaysWeatherDao,
    private val hoursWeatherDao: HoursWeatherDao,
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun loadData(city: String) {
        try {
            val requestResult = apiService.getForecast(q = city)

            val currentWeather = currentMapper.mapDtoToCurrentDbModel(requestResult)
            val hourList = hoursMapper.mapDtoToHoursListModelDb(requestResult)
            val dayList = daysMapper.mapDtoToDaysListModelDb(requestResult)

            currentWeatherDao.clearCurrentWeather()
            currentWeatherDao.insertCurrentWeather(currentWeather)

            hoursWeatherDao.clearHoursWeather()
            hoursWeatherDao.insertHoursWeatherList(hourList)

            daysWeatherDao.clearDaysWeather()
            daysWeatherDao.insertDaysWeatherList(dayList)

        } catch (e: Exception) {
            e.stackTraceToString()
        }
    }

    override suspend fun getHoursWeatherList(): List<WeatherEntity> {
        val listHoursItemModelDb = hoursWeatherDao.getHoursWeatherList()
        return hoursMapper.mapHourModelDbListToEntityList(listHoursItemModelDb)
    }

    override suspend fun getDaysWeatherList(): List<WeatherEntity> {
        val listDayItemModelDb = daysWeatherDao.getDaysWeatherList()
        return daysMapper.mapDaysModelDbListToEntityList(listDayItemModelDb)
    }

    override suspend fun getCurrentWeather(): WeatherEntity {
        val currentModelDB = currentWeatherDao.getCurrentWeather()
        return currentMapper.mapCurrentDbModelToEntity(currentModelDB)
    }

}