package com.octaneocatane.data.network

import com.octaneocatane.data.network.models.WeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/forecast.json?")
    suspend fun getForecast(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_CITY) q: String = "Moscow",
        @Query(QUERY_PARAM_DAYS) days: Int = 3,
        @Query(QUERY_PARAM_AQI) aqi: String = "no",
        @Query(QUERY_PARAM_ALERTS) alerts: String = "no",
    ): WeatherInfoDto

    companion object {
        private const val API_KEY = "be8f4a5e6d3d4498b73182435222306"
        private const val QUERY_PARAM_API_KEY = "key"
        private const val QUERY_PARAM_CITY = "q"
        private const val QUERY_PARAM_DAYS = "days"
        private const val QUERY_PARAM_AQI = "aqi"
        private const val QUERY_PARAM_ALERTS = "alerts"
    }
}