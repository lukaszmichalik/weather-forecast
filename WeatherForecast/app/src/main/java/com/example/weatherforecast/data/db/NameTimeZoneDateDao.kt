package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.network.response.CURRENTWEATHER_ID
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import retrofit2.Response

@Dao
interface NameTimeZoneDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(NameTimeZoneDate: CurrentWeatherResponse)

    @Query("select * from current_weather where id_current_weather=$CURRENTWEATHER_ID")
    fun loadNameTimeZoneDateTime(): LiveData<CurrentWeatherResponse>
}