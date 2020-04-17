package com.example.weatherforecast.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val MAIN_ID = 0

@Entity(tableName = "main")
data class Main(
    val pressure: Int,
    val temp: Double
){
    @PrimaryKey(autoGenerate = false)
    var id_m: Int = MAIN_ID
}