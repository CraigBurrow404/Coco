package com.burrow.sensorActivity2.ui.dataCapture

data class DataCaptureUiState(
    val firstCapture: Long = 0,
    val sensorName: String = "",
    val duration: Double = 0.0,
    var timestamp: Long = 0,
    var formattedTimestamp : String = "",
    val sensitivity: String = "",
    val captureSensorData: Boolean = false,
    val captureCount: Int = 0,
    val captureValueX: Float = 0f,
    val captureValueY: Float = 0f,
    val captureValueZ: Float = 0f,
    val dataCaptureButtonText: String = "Start",
    val captureRateHz: Int = 0,
    val selectedSensor: Int = 0,
    val mSensorListenerRegistered: Boolean = false,
    val uniqueId: Long = 0
)