package com.burrow.sensorActivity2.dataInterface.repository

import androidx.annotation.WorkerThread
import com.burrow.sensorActivity2.dataInterface.dao.CaptureDao
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class CaptureRepository(private val captureDao: CaptureDao) {

    @WorkerThread
    suspend fun insert(
        mSensorName : String,
        xValue : Float,
        yValue : Float,
        zValue : Float,
        mCaptureCount: Int,
        uniqueID: Long,
        mDuration: Double
    ) {
        val mUniqueID : Long = uniqueID
        val captureEntity = CaptureEntity(
            mCaptureCount,
            mUniqueID,
            mSensorName,
            mDuration,
            "",
            mCaptureCount,
            xValue,
            yValue,
            zValue
        )

        captureDao.insert(captureEntity)
    }

    @WorkerThread
    suspend fun getDataCaptureList() : List<DataCaptureSummary>{
        val dataCaptureSummaryList : List<DataCaptureSummary> = captureDao.getDataCaptureList()
        return dataCaptureSummaryList
    }

    @WorkerThread
        fun getCaptureList(uniqueID : Long): Flow<List<CaptureEntity>> {
        val graphDataList : Flow<List<CaptureEntity>> = captureDao.getGraphData(uniqueID)
        return graphDataList
    }

}