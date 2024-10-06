package com.burrow.sensorActivity2.dataInterface.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CaptureDBViewModel(private val mRepository: CaptureRepository) : ViewModel() {
    private val tag: String = "CaptureDBViewModel"

    fun insert(mCaptureData: CaptureData) = viewModelScope.launch {
            mRepository.insert(mCaptureData)
        }

    fun getBatchList(): Flow<List<CaptureEntity>> {
        val mBatchList : Flow<List<CaptureEntity>> = mRepository.getBatchList()
        return mBatchList
    }

    fun getNewBatchId(): Long {
        val mBatchId : Long = mRepository.getNewBatchId()
        return mBatchId
    }

    fun deleteAll() = viewModelScope.launch { mRepository.deleteAll() }

    fun getDataList(mBatchId : Long): Flow<List<CaptureEntity>> {
        val mDataList : Flow<List<CaptureEntity>> = mRepository.getDataList(mBatchId)
        return mDataList
    }

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