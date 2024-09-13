package com.burrow.sensorActivity2.ui.sensorDetails

import android.hardware.Sensor
import androidx.compose.material3.ButtonColors

data class SensorDetailsUiState(
    var uid: Int,
    var sensorName: String,
    var sensorVendor: String,
    var sensorVersion: Int,
    var sensorType: Int,
    var sensorMaxRange: Float,
    var sensorResolution: Float,
    var sensorPower: Float,
    var sensorMinDelay: Int,
    var selectedSensor: Int,
    val SensorSelected: Boolean,
    val SelectSensorButtonText: String

)