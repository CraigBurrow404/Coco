package com.burrow.sensorActivity2.ui.analyse

import android.util.Log
import androidx.lifecycle.ViewModel
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO - The Data List should be updated in the UI state using this approach

class AnalyseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyseUIState())
    private val uiState: StateFlow<AnalyseUIState> = _uiState.asStateFlow()
    private val tag: String = "AnalyseViewModel"

    fun getUniqueID(): Long {
        return uiState.value.uniqueId
    }

    fun setAnalyseUIState(mAnalyseUIState: AnalyseUIState) {

        _uiState.update { currentState ->
            currentState.copy(
                uniqueId = mAnalyseUIState.uniqueId,
                sensorName = mAnalyseUIState.sensorName,
                captureCount = mAnalyseUIState.captureCount
            )
        }
        Log.v(tag, "AnalyseViewModel Capture Count = ${uiState.value.captureCount}")
    }


    fun getCaptureList(mUniqueID: Long, mCaptureDBViewModel: CaptureDBViewModel)
            : Flow<List<CaptureEntity>> {
        Log.v(tag,"mUniqueID  $mUniqueID")
        val captureList = mCaptureDBViewModel.getCaptureList(mUniqueID)
        return captureList
    }
}