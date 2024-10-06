package com.burrow.sensorActivity2.ui.captureHistory

import android.content.Intent
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.burrow.sensorActivity2.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class CaptureHistoryViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(CaptureHistoryUIState())
    val uiState: StateFlow<CaptureHistoryUIState> = _uiState.asStateFlow()
    val batchId: Long = _uiState.value.batchId
    val timestamp: String = _uiState.value.timestamp

    fun updateBatchId(mBatchId: Long, mTimestamp: String) {

        _uiState.update { currentState ->
            currentState.copy(
                batchId = mBatchId,
                timestamp = mTimestamp
            )
        }
    }
}