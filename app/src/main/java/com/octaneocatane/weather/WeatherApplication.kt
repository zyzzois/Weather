package com.octaneocatane.weather

import android.app.Application
import com.octaneocatane.weather.di.DaggerApplicationComponent

class WeatherApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}