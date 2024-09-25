package com.burrow.sensorActivity2.ui.captureHistory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CaptureHistoryViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(CaptureHistoryUIState())
    val uiState: StateFlow<CaptureHistoryUIState> = _uiState.asStateFlow()
    val batchId: Int = _uiState.value.batchId
    val timestamp : String = _uiState.value.timestamp

    val tag: String = "AnalyseViewModel()"

    fun updateBatchId(mBatchId: Int, mTimestamp: String) {
        _uiState.update {
            currentState ->
            currentState.copy(
                batchId = mBatchId,
                timestamp = mTimestamp
            )
        }
    }
}