package com.example.weatherforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import com.example.weatherforecast.internal.NoConnectivityExeption
import com.example.weatherforecast.internal.Result
import java.io.IOException

class WeatherNetworkDataSourceImpl(
    private val apiWeatherOpenMap: ApiWeatherOpenMap
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String){
        try {
            val fetchedCurrentWeather = apiWeatherOpenMap
                .getCurrentWeather(location)
                .await()
            if(fetchedCurrentWeather.isSuccessful)
                _downloadedCurrentWeather.postValue(fetchedCurrentWeather.body())
             Result.Error(IOException("Error occurred during fetching movies!"))
        }
        catch(e: NoConnectivityExeption){
            Log.e("Connectivity","No internet connection", e)
        }

    }
}