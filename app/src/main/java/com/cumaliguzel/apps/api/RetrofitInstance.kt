package com.cumaliguzel.apps.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //we add the base url and get instance method
    private const val baseUrl = "https://api.weatherapi.com"
    //Retrofit Instance
    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)
}