package com.octaneocatane.weather.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.octaneocatane.weather.data.repository.WeatherRepositoryImpl
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.usecases.GetCurrentWeatherUseCase
import com.octaneocatane.weather.domain.usecases.GetDaysListUseCase
import com.octaneocatane.weather.domain.usecases.GetHoursListUseCase
import com.octaneocatane.weather.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherRepositoryImpl(application)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getCurrentWeatherListUseCase = GetCurrentWeatherUseCase(repository)
    private val getHoursListUseCase = GetHoursListUseCase(repository)
    private val getDaysListUseCase = GetDaysListUseCase(repository)

    private val _currentWeather = MutableLiveData<WeatherEntity>()
    val currentWeather: LiveData<WeatherEntity>
        get() = _currentWeather

    private val _hoursList = MutableLiveData<List<WeatherEntity>>()
    val hoursList: LiveData<List<WeatherEntity>>
        get() = _hoursList

    private val _daysList = MutableLiveData<List<WeatherEntity>>()
    val daysList: LiveData<List<WeatherEntity>>
        get() = _daysList


    fun loadData() {
        viewModelScope.launch {
            loadDataUseCase()
            _currentWeather.value = getCurrentWeatherListUseCase.invoke()
            _daysList.value = getDaysListUseCase.invoke()
            _hoursList.value = getHoursListUseCase.invoke()
        }
    }
}