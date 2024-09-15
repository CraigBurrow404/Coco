package com.burrow.sensorActivity2.dataInterface.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(captureEntity: CaptureEntity)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT unique_id as uniqueID , sensor_name as sensorName, 0 AS captureCount FROM data_capture_table")
    suspend fun getDataCaptureList() : List<DataCaptureSummary>

    @Query("SELECT * FROM data_capture_table WHERE unique_id = :uniqueID")
    fun getGraphData(uniqueID : Long): Flow<List<CaptureEntity>>

}