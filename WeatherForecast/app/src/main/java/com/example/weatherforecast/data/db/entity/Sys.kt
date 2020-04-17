package com.example.weatherforecast.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val SYS_ID = 0

@Entity(tableName = "sys")
data class Sys(
    val sunrise: Long,
    val sunset: Long
){
    @PrimaryKey(autoGenerate = false)
    var id_s: Int = SYS_ID

    /*fun formatTime(dateObject: Date): String  {
        val timeFormat: SimpleDateFormat = SimpleDateFormat("h:mm a")
        return timeFormat.format(dateObject)
    }*/
}
