package com.cumaliguzel.apps.viewModel

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumaliguzel.apps.api.Constant
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.api.RetrofitInstance
import com.cumaliguzel.apps.api.WeatherModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){
    private  val weatherApi = RetrofitInstance.weatherApi
    private  val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult


    fun getData(cityName : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey,cityName)
                if (response.isSuccessful){
                    response.body()?.let{
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }catch (e :Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getWeatherByCoordinates(lat: String, lon: String) {
        val coordinates = "$lat,$lon"
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeatherByCoordinates(Constant.apiKey, coordinates)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }





    fun  fetchLocation(context: android.content.Context,weatherViewModel: WeatherViewModel) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val lat = it.latitude.toString()
                    val lon = it.longitude.toString()
                    weatherViewModel.getWeatherByCoordinates(lat, lon)
                } ?: run {
                    println("Konum alınamadı. GPS kapalı olabilir.")
                }
            }.addOnFailureListener {
                println("Konum alma işlemi başarısız oldu: ${it.message}")
            }
        } else {
            println("Konum izni verilmemiş!")
        }
    }
}