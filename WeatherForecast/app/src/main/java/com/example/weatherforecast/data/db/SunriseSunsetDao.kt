package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.db.entity.SYS_ID
import com.example.weatherforecast.data.db.entity.Sys

@Dao
interface SunriseSunsetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(sys: Sys?)

    @Query("select * from sys")
    fun getSys(): LiveData<Sys?>
}