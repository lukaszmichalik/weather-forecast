package com.example.weatherforecast.data.provider




import android.content.Context
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse



const val CUSTOM_LOCATION = "CUSTOM_LOCATION"



class LocationProviderImpl(
    context: Context
) :PreferenceProvider(context), LocationProvider {


    override suspend fun hasLocationChanged(lastWeatherLocation: CurrentWeatherResponse): Boolean {


        return hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocation(): String {
        if(getCustomLocationName()==null){
            return "Gliwice"
        }
        else{
            return "${getCustomLocationName()}"
        }
    }


    private fun hasCustomLocationChanged(lastWeatherLocation: CurrentWeatherResponse): Boolean {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
    }


    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }
}
