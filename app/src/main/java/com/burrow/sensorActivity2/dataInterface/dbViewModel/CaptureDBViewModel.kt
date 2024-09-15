package com.burrow.sensorActivity2.dataInterface.dbViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.repository.CaptureRepository
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CaptureDBViewModel(private val repository: CaptureRepository) : ViewModel() {
    private val tag :String = "CaptureDBViewModel"


    suspend fun getDataCaptureList(): List<DataCaptureSummary> {
        Log.v(tag,"getDataCaptureList()")
        val dataCaptureSummaryList: List<DataCaptureSummary> = repository.getDataCaptureList()
        return dataCaptureSummaryList
    }

    fun getCaptureList(uniqueID : Long) : Flow<List<CaptureEntity>> {
        Log.v(tag,"getCaptureList(uniqueID : Long)")
        val captureList = repository.getCaptureList(uniqueID)
        return captureList
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