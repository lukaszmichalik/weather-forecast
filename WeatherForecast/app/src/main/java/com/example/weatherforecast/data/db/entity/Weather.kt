package com.example.weatherforecast.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val WEATHER_ID = 0

@Entity(tableName = "weather")
data class Weather(
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "icon")val icon: String
){
    @PrimaryKey(autoGenerate = false)
    var id_w: Int = WEATHER_ID
}