package com.example.weatherforecast.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.R
import com.example.weatherforecast.internal.glide.GlideApp
import com.example.weatherforecast.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()


    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI()= launch {
        val currentWeather = viewModel.weather.await()
        val currentSunriseSunset = viewModel.sunriseSunset.await()
        val currentWeatherResponse = viewModel.currentWeatherResponse.await()
        val weatherEntity = viewModel.descriptionIcon.await()

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperature(it.temp)
            Log.d("tag4",it.temp.toString())
            updatePressure(it.pressure)

       })

        currentWeatherResponse.observe(viewLifecycleOwner, Observer{
            if(it==null) return@Observer
            group_loading.visibility = View.GONE
            Log.d("tag3",it.timezone)
            updateLocation(it.name)
            Log.d("tag3",it.name)
            updateDate(it.dt,it.timezone)
            Log.d("tag1",it.timezone)

        currentSunriseSunset.observe(viewLifecycleOwner, Observer { sys ->
            if (sys == null) return@Observer

            group_loading.visibility = View.GONE
            updateSunrise(sys.sunrise, it.timezone)
            Log.d("in sys ",it.timezone)
            Log.d("sunrise in sys ",sys.sunrise.toString())
            updateSunset(sys.sunset, it.timezone)
            Log.d("tag2",it.timezone)
        })
        })

        weatherEntity.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDescription(it.description)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http://openweathermap.org/img/wn/${it.icon}@2x.png")
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Pogoda na dziś"
    }

    private fun updateTemperature(temperature: Double){
        textView_temperature.text="${Conversion(temperature)}°C"
    }

    private fun Conversion(n: Double): Int {
        return ((n-273.15f).toInt())

    }

    private fun updateSunrise(sunrise: Long, timezone: String){
        val updatedSunrise : Long = sunrise*1000L + timezone.toLong()*1000L
        textView_sunrise.text = "Wschód: " + getDate(updatedSunrise,"HH:mm ")
    }


    private fun updateSunset(sunset: Long, timezone: String){
        val updatedSunset : Long = sunset*1000L + timezone.toLong()*1000L
        textView_sunset.text =  "Zachód: " + getDate(updatedSunset,"HH:mm ")
    }

    /*private fun formatTime(dateObject: Date?): String? {
        val timeFormat = SimpleDateFormat("HH:MM ")
        return timeFormat.format(dateObject)
    }*/

    private fun updatePressure(pressure: Int){
        textView_pressure.text = "Ciśnienie: $pressure hPa"
    }

    private fun updateDate(date: Long, timezone: String){
        val dateTimezone = 1000L*(date + timezone.toLong())
        textView_date.text = getDate(dateTimezone, "HH:mm, d MMMM yyyy ")
    }

    private fun updateDescription(description: String){
        textView_description.text = description
    }

    private fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale("pl","PL"))
        //formatter.setTimeZone(TimeZone.getTimeZone("GMT"))

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}
