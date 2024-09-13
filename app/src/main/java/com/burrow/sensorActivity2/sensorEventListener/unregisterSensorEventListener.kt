package com.burrow.sensorActivity2.sensorEventListener

import android.hardware.SensorEventListener
import android.hardware.SensorManager

fun unregisterSensorEventListener(
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener
) {
    mSensorManager.unregisterListener(mSensorEventListener)
}