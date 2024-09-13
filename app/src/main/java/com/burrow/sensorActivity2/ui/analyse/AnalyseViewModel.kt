package com.burrow.sensorActivity2.ui.analyse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO - The Data List should be updated in the UIstate using this approach
/*class NewsViewModel(
    private val repository: NewsRepository,
    ...
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchArticles(category: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val newsItems = repository.newsItemsForCategory(category)
                _uiState.update {
                    it.copy(newsItems = newsItems)
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.update {
                    val messages = getMessagesFromThrowable(ioe)
                    it.copy(userMessages = messages)
                }
            }
        }
    }
}

 */
class AnalyseViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyseUIState())
    private val uiState: StateFlow<AnalyseUIState> = _uiState.asStateFlow()
    private val tag: String = "AnalyseViewModel"

    fun getUniqueID(): Long {
        return uiState.value.uniqueId
    }

    fun getSensorName(): String {
        return uiState.value.sensorName
    }

    fun getCaptureCount(): Int {
        return uiState.value.captureCount
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


    fun getGraphDataList(mUniqueID: Long, mCaptureDBViewModel: CaptureDBViewModel): MutableList<CaptureEntity> {
        Log.v(tag,"getGraphDataList Called, mUniqueId $mUniqueID")
        val graphDataList: MutableList<CaptureEntity> = mutableListOf()
        viewModelScope.launch(Dispatchers.IO) {
            Log.v(tag,"getGraphDataList viewModelScope.launch running")
            val mGraphDataListIn: List<CaptureEntity> = mCaptureDBViewModel.getGraphData(mUniqueID)
            for ((listCount, item: CaptureEntity) in mGraphDataListIn.withIndex()) {
                Log.v(tag,"listCount $listCount")
                val uid = mGraphDataListIn[listCount].uid
                val uniqueID = mGraphDataListIn[listCount].UniqueID
                val sensorName = mGraphDataListIn[listCount].sensorName
                val captureCount = mGraphDataListIn[listCount].captureCount
                val captureXValue = mGraphDataListIn[listCount].captureValueX
                val captureYValue = mGraphDataListIn[listCount].captureValueY
                val captureZValue = mGraphDataListIn[listCount].captureValueZ
                val duration = mGraphDataListIn[listCount].duration
                val sensitivity = mGraphDataListIn[listCount].sensitivity

                try {
                    graphDataList.add(
                        CaptureEntity(
                            uid = uid,
                            UniqueID = uniqueID,
                            sensorName = sensorName,
                            captureCount = captureCount,
                            captureValueX = captureXValue,
                            captureValueY = captureYValue,
                            captureValueZ = captureZValue,
                            duration = duration,
                            sensitivity = sensitivity
                        )
                    )
                    Log.v(tag, "graphDataList.add successful graphDataList.size " +
                            graphDataList.size.toString()
                    )
                } catch (e : Exception) {
                    Log.v(tag, "graphDataList.add failed")
                }
             //   Log.v(tag,"uniqueID $uniqueID, sensorName $sensorName, captureCount $captureCount")
            }
        }

        return graphDataList
    }
}