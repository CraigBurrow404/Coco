package com.burrow.sensorActivity2.ui.searchDataCapture

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchDataCaptureViewModel : ViewModel() {

    private lateinit var _selectedDataCaptureSummary: DataCaptureSummary
    private var _dataCaptureSummaryList: MutableList<DataCaptureSummary> = mutableListOf()
    private val _uiState = MutableStateFlow(SearchDataCaptureUiState())
    val tag : String = "SearchDataCaptureViewModel()"
    val uID: Long = _uiState.value.uid
    val sensorName: String = _uiState.value.sensorName

    fun rememberSelectedDataCapture(dataCaptureSummary: DataCaptureSummary) {
        _selectedDataCaptureSummary = dataCaptureSummary
    }

    fun getDataCaptureSummaryList(
        captureDBViewModel: CaptureDBViewModel
    ): MutableList<DataCaptureSummary> {

        _dataCaptureSummaryList.clear()

        viewModelScope.launch(Dispatchers.IO) {

            val dataCaptureListIn: List<DataCaptureSummary> =
                captureDBViewModel.getDataCaptureList()

            for ((listCount) in dataCaptureListIn.withIndex()) {

                val uniqueID =  dataCaptureListIn[listCount].uniqueID
                val sensorName = dataCaptureListIn[listCount].sensorName
                val captureCount = dataCaptureListIn[listCount].captureCount

                _dataCaptureSummaryList.add(
                    DataCaptureSummary(
                    uniqueID,
                    sensorName,
                    captureCount
                    )
                )
            }
        }
        Log.v(tag, "_dataCaptureSummaryList $_dataCaptureSummaryList")
        return _dataCaptureSummaryList
    }
}