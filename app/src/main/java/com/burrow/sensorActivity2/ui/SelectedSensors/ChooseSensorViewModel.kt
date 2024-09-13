package com.burrow.sensorActivity2.ui.SelectedSensors

import android.hardware.Sensor
import androidx.lifecycle.ViewModel


class ChooseSensorViewModel() : ViewModel() {

    private lateinit var _selectedSensor : Sensor

    fun rememberSelectedSensor(sensor: Sensor) {
        _selectedSensor = sensor
    }

    fun getSelectedSensor(): Sensor {
        return _selectedSensor
    }

}