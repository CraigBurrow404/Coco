package com.burrow.sensorActivity2.dataInterface.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burrow.sensorActivity2.dataInterface.Entity.SensorEntity

@Dao
interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sensorEntity: SensorEntity)

    @Query("DELETE FROM select_sensor_table")
    suspend fun deleteAll()

}
