package com.example.weatherforecast

import android.app.Application
import com.example.weatherforecast.data.db.ForecastDatabase
import com.example.weatherforecast.data.network.*
import com.example.weatherforecast.data.provider.LocationProvider
import com.example.weatherforecast.data.provider.LocationProviderImpl
import com.example.weatherforecast.data.repository.ForecastRepository
import com.example.weatherforecast.data.repository.ForecastRepositoryImpl
import com.example.weatherforecast.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().sunriseSunsetDao() }
        bind() from singleton { instance<ForecastDatabase>().nameTimeZoneDateDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance())}
        bind() from singleton { ApiWeatherOpenMap(instance())}
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance())}
        bind<LocationProvider>() with singleton {LocationProviderImpl(instance())}
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance(), instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}