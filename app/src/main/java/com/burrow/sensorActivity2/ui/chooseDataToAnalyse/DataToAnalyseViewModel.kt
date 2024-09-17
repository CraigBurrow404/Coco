package com.burrow.sensorActivity2.ui.chooseDataToAnalyse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataToAnalyseViewModel : ViewModel() {

    private lateinit var _selectedDataToAnalyseSummary: DataToAnalyseSummary
    private var _dataToAnalyseSummaryList: MutableList<DataToAnalyseSummary> = mutableListOf()
    private val _uiState = MutableStateFlow(DataToAnalyseUiState())
    val tag : String = "DataToAnalyseViewModel()"
    val uID: Long = _uiState.value.uid
    val sensorName: String = _uiState.value.sensorName

    fun rememberSelectedDataCapture(dataToAnalyseSummary: DataToAnalyseSummary) {
        Log.v(tag, "rememberSelectedDataCapture")
        _selectedDataToAnalyseSummary = dataToAnalyseSummary
    }

    fun getDataCaptureSummaryList(
        captureDBViewModel: CaptureDBViewModel
    ): MutableList<DataToAnalyseSummary> {

        Log.v(tag, "getDataCaptureSummaryList")
        _dataToAnalyseSummaryList.clear()

        viewModelScope.launch(Dispatchers.IO) {

            val dataCaptureListIn: List<DataToAnalyseSummary> =
                captureDBViewModel.getDataCaptureList()

            for ((listCount) in dataCaptureListIn.withIndex()) {

                val uniqueID =  dataCaptureListIn[listCount].uniqueID
                val sensorName = dataCaptureListIn[listCount].sensorName
                val captureCount = dataCaptureListIn[listCount].captureCount

                _dataToAnalyseSummaryList.add(
                    DataToAnalyseSummary(
                    uniqueID,
                    sensorName,
                    captureCount
                    )
                )
            }
        }
        return _dataToAnalyseSummaryList
    }
}