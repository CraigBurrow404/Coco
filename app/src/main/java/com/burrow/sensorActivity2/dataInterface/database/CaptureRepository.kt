package com.burrow.sensorActivity2.dataInterface.database

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

class CaptureRepository (private val captureDao: CaptureDao) {

    @WorkerThread
    fun getCaptureData(mBatchId: Long): Flow<List<CaptureEntity>> {
        val captureList: Flow<List<CaptureEntity>> = captureDao.getDataList(mBatchId)
        Log.v("CaptureRepository","batchId : $mBatchId captureList size : " +
                "${captureList.toString()}")
        return captureList
    }

    @WorkerThread
    fun getBatchList(): Flow<List<CaptureEntity>> {
        val batchList: Flow<List<CaptureEntity>> = captureDao.getBatchList()
        return batchList
    }

    @WorkerThread
    fun getDataList(mBatchId : Long): Flow<List<CaptureEntity>> {
        val dataList: Flow<List<CaptureEntity>> = captureDao.getDataList(mBatchId)
        return dataList
    }

    @WorkerThread
    fun getNewBatchId(): Long {
        val newBatchId: Long = captureDao.getNewBatchId()
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