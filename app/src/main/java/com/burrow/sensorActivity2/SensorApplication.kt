package com.burrow.sensorActivity2

import android.app.Application
import com.burrow.sensorActivity2.dataInterface.repository.CaptureRepository
import com.burrow.sensorActivity2.dataInterface.database.CaptureRoomDatabase
import com.burrow.sensorActivity2.dataInterface.repository.SensorRepository
import com.burrow.sensorActivity2.dataInterface.database.SensorRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SensorApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val dataCaptureDatabase by lazy { CaptureRoomDatabase.getDatabase(this, applicationScope) }
    val captureRepository by lazy { CaptureRepository(dataCaptureDatabase.dataCaptureDao()) }

    private val selectSensorDatabase by lazy { SensorRoomDatabase.getDatabase(this, applicationScope) }
    val sensorRepository by lazy { SensorRepository(selectSensorDatabase.selectSensorDao()) }

}