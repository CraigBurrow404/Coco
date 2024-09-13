package com.burrow.sensorActivity2.ui.home

import androidx.compose.material3.ButtonColors
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor

data class HomeUiState(
    val sensorName: String = "",
    val duration: String = "",
    val sensitivity: String = "",
    val captureSensorData: Boolean = false,
    val captureCount: Int = 0,
    val captureValueX: Float = 0f,
    val captureValueY: Float = 0f,
    val captureValueZ: Float = 0f,
    val selectedSensor: Int = 0
)