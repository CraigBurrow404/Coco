package com.burrow.sensorActivity2.dataInterface.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.database.CaptureRoomDatabase.Companion.getDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CaptureDBViewModel(private val mRepository: CaptureRepository) : ViewModel() {
    private val tag: String = "CaptureDBViewModel"

    fun insert(mCaptureData: CaptureData) = viewModelScope.launch {
            mRepository.insert(mCaptureData)
        }

    fun getCaptureData(mUniqueID : Long): Flow<List<CaptureEntity>> {
        val mCaptureList = mRepository.getCaptureData(mUniqueID)
        return mCaptureList
    }

    fun getCaptureList(): Flow<List<CaptureEntity>> {
        val mCaptureSummaryList : Flow<List<CaptureEntity>> = mRepository.getCaptureList()
        return mCaptureSummaryList
    }

    fun getNewBatchId(): Int {
        val mBatchId : Int = mRepository.getNewBatchId()
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