package com.cumaliguzel.apps.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherApi {

    //get weather
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Response<WeatherModel>

    // get current location user
    @GET("/v1/current.json")
    suspend fun getWeatherByCoordinates(
        @Query("key") apiKey: String,
        @Query("q") coordinates: String
    ): Response<WeatherModel>

}