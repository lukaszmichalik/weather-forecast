package com.example.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.*
import com.example.weatherforecast.data.db.entity.Main
import com.example.weatherforecast.data.db.entity.Sys
import com.example.weatherforecast.data.db.entity.Weather
import com.example.weatherforecast.data.network.WeatherNetworkDataSource
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import com.example.weatherforecast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val sunriseSunsetDao: SunriseSunsetDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val nameTimeZoneDateDao: NameTimeZoneDateDao,
    private val weatherDao: WeatherDao,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getTempAndPressure(): LiveData<out Main?> {
       return withContext(Dispatchers.IO){
           initWeatherData()
           return@withContext currentWeatherDao.getMain()
       }
    }

    override suspend fun getSunriseAndSunset(): LiveData<out Sys?> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext sunriseSunsetDao.getSys()
        }
    }

    override suspend fun loadNameTimeZoneDate(): LiveData<out CurrentWeatherResponse?> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext nameTimeZoneDateDao.loadNameTimeZoneDateTime()
        }
    }

    override suspend fun loadDescriptionAndIcon(): LiveData<out Weather?> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext weatherDao.loadDescriptionAndIcon()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
            GlobalScope.launch(Dispatchers.IO) {
                nameTimeZoneDateDao.upsert(fetchedWeather)
                currentWeatherDao.upsert(fetchedWeather.main)
                sunriseSunsetDao.upsert(fetchedWeather.sys)
                weatherDao.upsert(fetchedWeather.weather)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = nameTimeZoneDateDao.loadNameTimeZoneDateTime().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocation()
        )
    }

    private  fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}