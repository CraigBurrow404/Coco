package com.burrow.sensorActivity2.dataInterface.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burrow.sensorActivity2.ui.chooseDataToAnalyse.DataToAnalyseSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(captureEntity: CaptureEntity)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT batch_id as uniqueID , sensor_name as sensorName, 0 AS captureCount FROM data_capture_table")
    fun getDataCaptureList() : Flow<List<DataToAnalyseSummary>>

    @Query("SELECT * FROM data_capture_table WHERE batch_id = :batchId")
    fun getCapture(batchId : Long): Flow<List<CaptureEntity>>

    @Query("SELECT DISTINCT uid," +
            "batch_id," +
            "0 as timestamp," +
            "sensor_name," +
            "0.0 as duration," +
            "'' as sensitivity," +
            "0 as capture_count," +
            "0.0 as capture_valueX," +
            "0.0 as capture_valueY," +
            "0.0 as capture_valueZ" +
            " FROM data_capture_table")
    fun getCaptureSummaryList(): Flow<List<CaptureEntity>>

    @Query("Select Max(batch_id) from data_capture_table")
    fun getNewBatchId(): Int

}