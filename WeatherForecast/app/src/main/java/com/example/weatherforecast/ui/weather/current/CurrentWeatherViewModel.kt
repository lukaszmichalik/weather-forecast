package com.example.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.repository.ForecastRepository
import com.example.weatherforecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
        val weather by lazyDeferred {
            forecastRepository.getTempAndPressure()
        }

        val sunriseSunset by lazyDeferred {
            forecastRepository.getSunriseAndSunset()
        }

        val currentWeatherResponse by lazyDeferred {
            forecastRepository.loadNameTimeZoneDate()
        }

        val descriptionIcon by lazyDeferred {
            forecastRepository.loadDescriptionAndIcon()
        }
}
