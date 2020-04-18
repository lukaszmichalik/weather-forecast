package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

@Dao
interface NameTimeZoneDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(NameTimeZoneDate: CurrentWeatherResponse?)

    @Query("select * from current_weather")
    fun loadNameTimeZoneDateTime(): LiveData<CurrentWeatherResponse?>
}