package com.burrow.sensorActivity2.dataInterface.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CaptureDBViewModel(private val mRepository: CaptureRepository) : ViewModel() {
    private val tag: String = "CaptureDBViewModel"

    fun insert(mCaptureEntity: CaptureEntity) = viewModelScope.launch {
            mRepository.insert(mCaptureEntity)
        }

    fun getCaptureList(mUniqueID : Long): Flow<List<CaptureEntity>> {
        val mCaptureList = mRepository.getCaptureList(mUniqueID)
        return mCaptureList
    }

    fun getCaptureSummaryList(): Flow<List<CaptureEntity>> {
        val mCaptureSummaryList : Flow<List<CaptureEntity>> = mRepository.getCaptureSummaryList()
        return mCaptureSummaryList
    }

    fun getNewBatchId(): Any {
        val mBatchId : Flow<Int> = mRepository.getNewBatchId()
        return mBatchId
    }

    fun deleteAll() = viewModelScope.launch { mRepository.deleteAll() }

}

class CaptureDBViewModelFactory(private val captureRepository: CaptureRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaptureDBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaptureDBViewModel(captureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}