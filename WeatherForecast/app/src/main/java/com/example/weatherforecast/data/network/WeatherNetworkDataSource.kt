package com.example.weatherforecast.data.network

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import com.example.weatherforecast.internal.Result
import retrofit2.Response

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}