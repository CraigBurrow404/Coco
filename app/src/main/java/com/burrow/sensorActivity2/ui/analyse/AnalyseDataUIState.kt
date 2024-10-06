package com.burrow.sensorActivity2.ui.analyse

data class AnalyseUIState(
    var batchId : Long = 0L,
    var sensorName: String = "",
    var captureCount: Int = 0,
)