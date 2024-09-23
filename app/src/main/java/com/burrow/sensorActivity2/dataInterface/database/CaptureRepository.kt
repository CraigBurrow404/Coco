package com.burrow.sensorActivity2.dataInterface.database

import androidx.annotation.WorkerThread
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class CaptureRepository (private val captureDao: CaptureDao) {

    @WorkerThread
    fun getCaptureList(batchId: Long): Flow<List<CaptureEntity>> {
        val captureList: Flow<List<CaptureEntity>> = captureDao.getCapture(batchId)
        return captureList
    }

    @WorkerThread
    fun getCaptureSummaryList(): Flow<List<CaptureEntity>> {
        val captureSummaryList: Flow<List<CaptureEntity>> = captureDao.getCaptureSummaryList()
        return captureSummaryList
    }

    @WorkerThread
    fun getNewBatchId(): Int {
        val newBatchId: Int = captureDao.getNewBatchId()
        return newBatchId
    }

    @WorkerThread
    suspend fun insertFirstRow() {
        val captureEntity : CaptureEntity =
            CaptureEntity(
                uid = 0,
                batchId = 0,
                timestamp = 0,
                captureCount = 0,
                captureValueY = 0f,
                captureValueX = 0f,
                captureValueZ = 0f,
                duration = 0.0,
                sensitivity = "Dummy First Row",
                sensorName = "Dummy First Row"
            )
        captureDao.insert(captureEntity)
    }

    @Delete
    suspend fun deleteAll() = captureDao.deleteAll()

    @WorkerThread
    suspend fun insert(captureEntity: CaptureEntity) {
        captureDao.insert(captureEntity)
    }

}