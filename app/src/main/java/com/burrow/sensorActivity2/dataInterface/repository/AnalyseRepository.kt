package com.burrow.sensorActivity2.dataInterface.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.Dao.CaptureDao
import com.burrow.sensorActivity2.dataInterface.Dao.SensorDao
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.Entity.SensorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class AnalyseRepository(private val captureDao: CaptureDao) {

    @WorkerThread
    suspend fun getGraphData(uniqueID: Long) : List<CaptureEntity>{
        val captureEntityList : List<CaptureEntity> = captureDao.getGraphData( uniqueID )
        return captureEntityList
    }
}