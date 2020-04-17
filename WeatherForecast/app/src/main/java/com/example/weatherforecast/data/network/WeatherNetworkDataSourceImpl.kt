package com.example.weatherforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import com.example.weatherforecast.internal.NoConnectivityExeption

class WeatherNetworkDataSourceImpl(
    private val apiWeatherOpenMap: ApiWeatherOpenMap
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = apiWeatherOpenMap
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch(e: NoConnectivityExeption){
            Log.e("Connectivity","No internet connection", e)
        }

    }
}