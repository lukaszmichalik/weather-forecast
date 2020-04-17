package com.example.weatherforecast.data.provider

import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: CurrentWeatherResponse): Boolean
    suspend fun getPreferredLocation(): String
}
