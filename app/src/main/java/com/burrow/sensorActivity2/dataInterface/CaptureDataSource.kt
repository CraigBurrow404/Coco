package com.burrow.sensorActivity2.dataInterface

import com.burrow.sensorActivity2.dataInterface.database.Capture
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.database.dataCaptureDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CaptureDataSource(
    private val refreshIntervalMs: Long = 5000
    ) {
        val captures: Flow<List<CaptureEntity>> = dataCaptureDao().getCapture(1)
}