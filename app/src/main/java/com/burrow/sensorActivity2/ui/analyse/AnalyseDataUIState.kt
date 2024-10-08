package com.burrow.sensorActivity2.ui.analyse

import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity

data class AnalyseUIState(
    var batchId : Long = 0L,
    var sensorName: String = "",
    var captureCount: Int = 0,
    var captureList : List<CaptureEntity>? = null
)