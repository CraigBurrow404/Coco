package com.burrow.sensorActivity2.dataInterface.repository

import android.hardware.SensorEvent
import androidx.annotation.WorkerThread
import com.burrow.sensorActivity2.dataInterface.Dao.CaptureDao
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.ui.charts.graphData
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary

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
    suspend fun deleteAll() {
        captureDao.deleteAll()
    }



    @WorkerThread
    suspend fun getDataCaptureList() : List<DataCaptureSummary>{
        val dataCaptureSummaryList : List<DataCaptureSummary> = captureDao.getDataCaptureList()
        return dataCaptureSummaryList
    }

    @WorkerThread
    suspend fun getGraphData(uniqueID : Long): List<CaptureEntity> {
        val graphDataList : List<CaptureEntity> = captureDao.getGraphData(uniqueID)
        return graphDataList
    }

}