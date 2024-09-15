package com.burrow.sensorActivity2.ui.selectedSensors

import android.hardware.Sensor
import androidx.lifecycle.ViewModel


class ChooseSensorViewModel : ViewModel() {

    private lateinit var _selectedSensor : Sensor

    fun rememberSelectedSensor(sensor: Sensor) {
        _selectedSensor = sensor
    }
}