package com.burrow.sensorActivity2.dataInterface.database

data class CaptureData(
    val batchId : Long,
    val firstCapture : Long,
    val timestamp: Long,
    val sensorName: String,
    val duration: Double,
    val sensitivity: String,
    val captureCount: Int,
    val captureValueX: Float,
    val captureValueY: Float,
    val captureValueZ: Float
)