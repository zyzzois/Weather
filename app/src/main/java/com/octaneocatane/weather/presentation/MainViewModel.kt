package com.octaneocatane.weather.presentation

import androidx.lifecycle.*
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.domain.usecases.GetCurrentWeatherUseCase
import com.octaneocatane.weather.domain.usecases.GetDaysListUseCase
import com.octaneocatane.weather.domain.usecases.GetHoursListUseCase
import com.octaneocatane.weather.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getCurrentWeatherListUseCase: GetCurrentWeatherUseCase,
    private val getHoursListUseCase: GetHoursListUseCase,
    private val getDaysListUseCase: GetDaysListUseCase
) : ViewModel() {

    private val _currentWeather = MutableLiveData<WeatherEntity>()
    val currentWeather: LiveData<WeatherEntity>
        get() = _currentWeather

    private val _hoursList = MutableLiveData<List<WeatherEntity>>()
    val hoursList: LiveData<List<WeatherEntity>>
        get() = _hoursList

    private val _daysList = MutableLiveData<List<WeatherEntity>>()
    val daysList: LiveData<List<WeatherEntity>>
        get() = _daysList

    fun setDataToCurrentCard(data: WeatherEntity) {
        _currentWeather.value = data

    }

    fun loadData(city: String) {
        viewModelScope.launch {
            loadDataUseCase(city)
            _currentWeather.value = getCurrentWeatherListUseCase.invoke()
            _daysList.value = getDaysListUseCase.invoke()
            _hoursList.value = getHoursListUseCase.invoke()
        }
    }
}