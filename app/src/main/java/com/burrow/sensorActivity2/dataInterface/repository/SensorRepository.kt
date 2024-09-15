package com.burrow.sensorActivity2.dataInterface.repository

import androidx.annotation.WorkerThread
import com.burrow.sensorActivity2.dataInterface.dao.SensorDao
import com.burrow.sensorActivity2.dataInterface.entity.SensorEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class SensorRepository(private val sensorDao: SensorDao) {
    @WorkerThread
    suspend fun insert( mSelectSensorRow : SensorEntity) {
        sensorDao.insert(mSelectSensorRow)
    }
}