package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.db.entity.WEATHER_ID
import com.example.weatherforecast.data.db.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weather: ArrayList<Weather?>?)

    @Query("select * from weather ")
    fun loadDescriptionAndIcon() : LiveData<Weather>
}