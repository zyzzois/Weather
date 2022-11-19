package com.octaneocatane.weather.presentation.viewmodel

import com.octaneocatane.domain.entity.WeatherEntity
import com.octaneocatane.domain.usecase.GetCurrentWeatherUseCase
import com.octaneocatane.domain.usecase.GetDaysListUseCase
import com.octaneocatane.domain.usecase.GetHoursListUseCase
import com.octaneocatane.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.launch
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getCurrentWeatherListUseCase: GetCurrentWeatherUseCase,
    private val getHoursListUseCase: GetHoursListUseCase,
    private val getDaysListUseCase: GetDaysListUseCase,
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

    private val _networkConnectionEnabled = MutableLiveData<Boolean>()
    val networkConnectionEnabled: LiveData<Boolean>
        get() = _networkConnectionEnabled

    private val _locationEnabled = MutableLiveData<Boolean>()
    val locationEnabled: LiveData<Boolean>
        get() = _locationEnabled

    private val _currentLocationCoordinates = MutableLiveData<String>()
    val currentLocationCoordinates: LiveData<String>
        get() = _currentLocationCoordinates

    fun loadData(city: String) {
        viewModelScope.launch {
            loadDataUseCase(city)
            _currentWeather.value = getCurrentWeatherListUseCase.invoke()
            _daysList.value = getDaysListUseCase.invoke()
            _hoursList.value = getHoursListUseCase.invoke()
        }
    }

    fun checkNetworkAndLocationStatus(activity: Activity) {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE)
                as LocationManager
        val connectivity = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        _locationEnabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        _networkConnectionEnabled.value = connectivity.activeNetworkInfo != null
                && connectivity.activeNetworkInfo!!.state == NetworkInfo.State.CONNECTED
    }

    fun getCurrentLocation(context: Context) {
        val fLocationClient by lazy {
            LocationServices.getFusedLocationProviderClient(context)
        }
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                _currentLocationCoordinates.value = "${it.result.latitude},${it.result.longitude}"
                loadData(_currentLocationCoordinates.value.toString())
            }
    }

}