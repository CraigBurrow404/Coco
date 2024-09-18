package com.burrow.sensorActivity2.dataInterface.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.ui.chooseDataToAnalyse.DataToAnalyseSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(captureEntity: CaptureEntity)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT unique_id as uniqueID , sensor_name as sensorName, 0 AS captureCount FROM data_capture_table")
    suspend fun getDataCaptureList() : List<DataToAnalyseSummary>

    @Query("SELECT * FROM data_capture_table WHERE unique_id = :uniqueID")
    fun getGraphData(uniqueID : Long): Flow<List<CaptureEntity>>

    @Query("SELECT * FROM data_capture_table WHERE unique_id = :uniqueID")
    fun getAnalyseData(uniqueID : Long): LiveData<List<CaptureEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCapture(captureEntity: CaptureEntity)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAllCaptures()

    @Query("SELECT DISTINCT unique_id as uniqueID , sensor_name as sensorName, 0 AS captureCount FROM data_capture_table")
    suspend fun getCaptureList() : List<DataToAnalyseSummary>

    @Query("SELECT * FROM data_capture_table WHERE unique_id = :uniqueID")
    fun getCapture(uniqueID : Long): Flow<List<CaptureEntity>>

}