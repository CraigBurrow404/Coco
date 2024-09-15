package com.burrow.sensorActivity2.ui.sensorDetails

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
    val sensorSelected: Boolean,
    val selectSensorButtonText: String

)