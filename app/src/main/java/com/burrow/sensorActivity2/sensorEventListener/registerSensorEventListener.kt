package com.burrow.sensorActivity2.sensorEventListener

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager

fun registerSensorEventListener(
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    mSelectedSensor: Int
) {
    val mSensor: Sensor? = mSensorManager.getDefaultSensor(mSelectedSensor)
    mSensorManager.registerListener(mSensorEventListener, mSensor, 10000)
}