package com.burrow.sensorActivity2.dataInterface.dbViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.repository.CaptureRepository
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CaptureDBViewModel(private val repository: CaptureRepository) : ViewModel() {
    private var _graphData: MutableList<CaptureEntity> = mutableListOf()
    private val tag :String = "CaptureDBViewModel"

    suspend fun getDataCaptureList(): List<DataCaptureSummary> {
        val dataCaptureSummaryList: List<DataCaptureSummary> = repository.getDataCaptureList()
        return dataCaptureSummaryList
    }

    suspend fun getGraphData(uniqueID : Long) : List<CaptureEntity> {
        val graphDataList = repository.getGraphData(uniqueID)
        Log.v(tag,"GraphDataList Size ${graphDataList.size}, uniqueId $uniqueID")
        return graphDataList
    }

    fun insert(
        mSensorName: String,
        xValue: Float,
        yValue: Float,
        zValue: Float,
        mCaptureCount: Int,
        uniqueID: Long,
        duration: Double
    ) = viewModelScope.launch {
        repository.insert(
            mSensorName,
            xValue,
            yValue,
            zValue,
            mCaptureCount,
            uniqueID,
            duration
        )
    }

    fun deleteALL() = viewModelScope.launch { repository.deleteAll() }

}

class CaptureDBViewModelFactory(private val repository: CaptureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaptureDBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaptureDBViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}