package com.octaneocatane.weather.di

import android.app.Application
import android.content.Context
import com.octaneocatane.weather.presentation.MainActivity
import com.octaneocatane.weather.presentation.fragments.DaysFragment
import com.octaneocatane.weather.presentation.fragments.HoursFragment
import com.octaneocatane.weather.presentation.fragments.MainFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class, ViewModelModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: HoursFragment)
    fun inject(fragment: DaysFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}