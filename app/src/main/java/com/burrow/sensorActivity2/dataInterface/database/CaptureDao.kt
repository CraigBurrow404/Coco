package com.burrow.sensorActivity2.dataInterface.database

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CaptureDao {

    @Insert(entity = CaptureEntity::class,onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(captureData: CaptureData)

    @Query("DELETE FROM data_capture_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM data_capture_table WHERE batchId = :batchId")
    fun getCaptureData(batchId : Long): Flow<List<CaptureEntity>>

    @Query("SELECT DISTINCT 0 as uid," +
            "batchId," +
            "firstCapture," +
            "0 as timestamp," +
            "'' as sensorName," +
            "0.0 as duration," +
            "'' as sensitivity," +
            "0 as captureCount," +
            "0.0 as captureValueX," +
            "0.0 as captureValueY," +
            "0.0 as captureValueZ" +
            " FROM data_capture_table")
    fun getCaptureList(): Flow<List<CaptureEntity>>

    @Query("Select Max(batchId)  + 1 from data_capture_table")
    fun getNewBatchId(): Int

}