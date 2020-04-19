package com.example.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.entity.Main
import com.example.weatherforecast.data.db.entity.Sys
import com.example.weatherforecast.data.db.entity.Weather
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getTempAndPressure(): LiveData<out Main>
    suspend fun getSunriseAndSunset(): LiveData<out Sys>
    suspend fun loadNameTimeZoneDate(): LiveData<out CurrentWeatherResponse>
    suspend fun loadDescriptionAndIcon(): LiveData<out Weather>
}