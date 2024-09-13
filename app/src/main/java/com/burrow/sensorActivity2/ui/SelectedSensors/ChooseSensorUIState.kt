package com.burrow.sensorActivity2.ui.SelectedSensors

import android.hardware.Sensor

data class ChooseSensorUiState(
    var uid: Int,
    var sensorName: String,
    var sensorVendor: String,
    var sensorVersion: Int,
    var sensorType: Int,
    var sensorMaxRange: Float,
    var sensorResolution: Float,
    var sensorPower: Float,
    var sensorMinDelay: Int,
    var sensorList: MutableList<Sensor>
)