package com.burrow.sensorActivity2.ui.chooseDataToAnalyse

import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity

data class AnalyseScreenUIState (
    val batchId : Int = 0,
    val analyseCaptureList : List<CaptureEntity>? = null
)