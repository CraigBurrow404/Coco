package com.burrow.sensorActivity2.ui.chooseDataToAnalyse

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChooseAnalyseViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(AnalyseScreenUIState())
    val uiState: StateFlow<AnalyseScreenUIState> = _uiState.asStateFlow()
    val batchId: Int = _uiState.value.batchId

    val tag: String = "AnalyseViewModel()"

    fun updateBatchId(mBatchId: Int) {
        _uiState.update {
            currentState ->
            currentState.copy(
                batchId = mBatchId,
                analyseCaptureList = null
            )
        }
    }
}