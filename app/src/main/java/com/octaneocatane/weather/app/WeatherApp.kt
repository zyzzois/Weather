package com.octaneocatane.weather.app

import android.app.Application
import com.octaneocatane.weather.di.DaggerApplicationComponent

class WeatherApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}