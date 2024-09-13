package com.burrow.sensorActivity2.ui.SelectedSensors

import android.hardware.Sensor

data class SearchDataCaptureUiState(
    var uid: Long = 0,
    var sensorName: String = "",
    var sensorVendor: String = "",
    var sensorVersion: Int = 0,
    var sensorType: Int = 0,
    var sensorMaxRange: Float = 0f,
    var sensorResolution: Float = 0f,
    var sensorPower: Float = 0f,
    var sensorMinDelay: Int = 0,
    var sensorList: MutableList<Sensor> = mutableListOf()
)