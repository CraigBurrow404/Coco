package com.burrow.sensorActivity2.ui.searchDataCapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.ui.SelectedSensors.SearchDataCaptureUiState
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchDataCaptureViewModel() : ViewModel() {

    private lateinit var _selectedDataCaptureSummary: DataCaptureSummary
    private var _dataCaptureSummaryList: MutableList<DataCaptureSummary> = mutableListOf()

    private val _uiState = MutableStateFlow(SearchDataCaptureUiState())
    val uiState: StateFlow<SearchDataCaptureUiState> = _uiState.asStateFlow()

    val uID: Long = _uiState.value.uid
    val sensorName: String = _uiState.value.sensorName

    fun rememberSelectedDataCapture(dataCaptureSummary: DataCaptureSummary) {
        _selectedDataCaptureSummary = dataCaptureSummary
    }

    fun getSelectedDataCapture(): DataCaptureSummary {
        return _selectedDataCaptureSummary
    }

    fun getDataCaptureSummaryList(
        captureDBViewModel: CaptureDBViewModel
    ): MutableList<DataCaptureSummary> {

        _dataCaptureSummaryList.clear()

        viewModelScope.launch(Dispatchers.IO) {

            val dataCaptureListIn: List<DataCaptureSummary> =
                captureDBViewModel.getDataCaptureList()

            for ((listCount, item: DataCaptureSummary) in dataCaptureListIn.withIndex()) {

                val uniqueID =  dataCaptureListIn[listCount].unique_id
                val sensorName = dataCaptureListIn[listCount].sensor_name
                val captureCount = dataCaptureListIn[listCount].capture_count

                println("Using For Loop $listCount")

                _dataCaptureSummaryList.add(
                    DataCaptureSummary(
                    uniqueID,
                    sensorName,
                    captureCount
                    )
                )

                println("Summary List size $_dataCaptureSummaryList.size")
            }
        }
        return _dataCaptureSummaryList
    }
}