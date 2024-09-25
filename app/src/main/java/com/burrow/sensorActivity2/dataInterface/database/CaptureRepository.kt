package com.burrow.sensorActivity2.dataInterface.database

import androidx.annotation.WorkerThread
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class CaptureRepository (private val captureDao: CaptureDao) {

    @WorkerThread
    fun getCaptureData(batchId: Long): Flow<List<CaptureEntity>> {
        val captureList: Flow<List<CaptureEntity>> = captureDao.getCaptureData(batchId)
        return captureList
    }

    @WorkerThread
    fun getCaptureList(): Flow<List<CaptureEntity>> {
        val captureList: Flow<List<CaptureEntity>> = captureDao.getCaptureList()
        return captureList
    }

    @WorkerThread
    fun getNewBatchId(): Int {
        val newBatchId: Int = captureDao.getNewBatchId()
        return newBatchId
    }

    @WorkerThread
    suspend fun insertFirstRow() {
        val captureData : CaptureData =
            CaptureData(
                batchId = 0,
                firstCapture = 0,
                timestamp = 0,
                captureCount = 0,
                captureValueY = 0f,
                captureValueX = 0f,
                captureValueZ = 0f,
                duration = 0.0,
                sensitivity = "Dummy First Row",
                sensorName = "Dummy First Row"
            )
        captureDao.insert(captureData)
    }

    @Delete
    suspend fun deleteAll() = captureDao.deleteAll()

    @WorkerThread
    suspend fun insert(captureData: CaptureData) {
        captureDao.insert(captureData)
    }

}