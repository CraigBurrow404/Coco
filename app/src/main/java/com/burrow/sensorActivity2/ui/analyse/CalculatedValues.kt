package com.burrow.sensorActivity2.ui.analyse

data class CalculatedValues (
    val timestamp : String,
    val averageSpeed : Double,
    val totalDistance : Double,
    val totalTime : Double,
    val timeToEachMetre : MutableList<Double>,
    val timeToEachTenMetres : MutableList<Double>
)
