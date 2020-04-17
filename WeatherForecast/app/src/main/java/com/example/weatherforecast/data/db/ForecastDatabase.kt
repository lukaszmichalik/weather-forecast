package com.example.weatherforecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.data.db.entity.Main
import com.example.weatherforecast.data.db.entity.Sys
import com.example.weatherforecast.data.db.entity.Weather
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

@Database(
    entities = [Main::class, Sys::class, CurrentWeatherResponse::class, Weather::class],
    version = 1
)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun sunriseSunsetDao(): SunriseSunsetDao
    abstract fun nameTimeZoneDateDao(): NameTimeZoneDateDao
    abstract fun weatherDao(): WeatherDao

    companion object{
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "forecast.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}