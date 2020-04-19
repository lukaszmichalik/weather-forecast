package com.example.weatherforecast.internal

import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: CurrentWeatherResponse?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}