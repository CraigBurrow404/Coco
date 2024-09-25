package com.burrow.sensorActivity2.ui.analyse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.burrow.sensorActivity2.SensorApplication
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.database.CaptureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO - The Data List should be updated in the UI state using this approach

class AnalyseViewModel(
    private val captureRepository: CaptureRepository,
    private val savedStateHandle: SavedStateHandle
)  : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyseUIState())
    private val uiState: StateFlow<AnalyseUIState> = _uiState.asStateFlow()
    private val tag: String = "AnalyseViewModel"

    fun getCaptureData(mUniqueID: Long)
            : Flow<List<CaptureEntity>> = captureRepository.getCaptureData(mUniqueID)

    fun getUniqueID(): Long {
//        return uiState.value.uniqueId
        return 0
    }

    fun setAnalyseUIState(mAnalyseUIState: AnalyseUIState) {

  /*      _uiState.update { currentState ->
            currentState.copy(
                uniqueId = mAnalyseUIState.uniqueId,
                sensorName = mAnalyseUIState.sensorName,
                captureCount = mAnalyseUIState.captureCount)
        }
        */
    }

    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return AnalyseViewModel(
                    (application as SensorApplication).captureRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}