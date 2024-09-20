package com.burrow.sensorActivity2.dataInterface.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sensorEntity: SensorEntity)

    @Query("DELETE FROM select_sensor_table")
    suspend fun deleteAll()

}
