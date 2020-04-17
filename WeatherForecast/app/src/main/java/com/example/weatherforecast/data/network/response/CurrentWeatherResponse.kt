package com.example.weatherforecast.data.network.response

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.example.weatherforecast.data.db.entity.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime


const val CURRENTWEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    @ColumnInfo(name = "date") val dt: Long,
    @Embedded val main: Main?,
    @ColumnInfo(name = "name") val name: String,
    @Embedded val sys: Sys?,
    @ColumnInfo(name = "time_zone") val timezone: String,
    @Embedded val weather: ArrayList<Weather?>?

){
    @PrimaryKey(autoGenerate = false)
    var id: Int? = CURRENTWEATHER_ID
    val zonedDateTime: ZonedDateTime
        get(){
            val instant = Instant.ofEpochSecond(dt)
            val zoneId = ZoneId.of(timezone)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}