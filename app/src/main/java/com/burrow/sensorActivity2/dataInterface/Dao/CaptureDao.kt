package com.burrow.sensorActivity2.dataInterface.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary

@Dao
interface CaptureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(captureEntity: CaptureEntity)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT unique_id , sensor_name, 0 AS capture_count FROM data_capture_table")
    suspend fun getDataCaptureList() : List<DataCaptureSummary>

    @Query("SELECT * FROM data_capture_table WHERE unique_id = :uniqueID")
    suspend fun getGraphData(uniqueID : Long): List<CaptureEntity>

}