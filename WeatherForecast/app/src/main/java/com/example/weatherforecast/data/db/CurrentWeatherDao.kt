package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.db.entity.MAIN_ID
import com.example.weatherforecast.data.db.entity.Main

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(main: Main?)

    @Query("select * from main where id_m = $MAIN_ID")
    fun getMain(): LiveData<Main?>

}