package com.burrow.sensorActivity2.dataInterface.database

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.burrow.sensorActivity2.dataInterface.dao.CaptureDao
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import com.burrow.sensorActivity2.ui.chooseDataToAnalyse.DataToAnalyseSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Database(entities = [CaptureEntity::class], version = 7, exportSchema = false)
abstract class CaptureRoomDatabase : RoomDatabase() {


    suspend fun getDataCaptureList(): List<DataToAnalyseSummary> {
        Log.v(tag, "getDataCaptureList()")
        val dataToAnalyseSummaryList: List<DataToAnalyseSummary> = getDataCaptureList()
        return dataToAnalyseSummaryList
    }

    fun getCaptureList(uniqueID: Long): Flow<List<CaptureEntity>> {
        Log.v(tag, "getCaptureList(uniqueID : Long)")
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


    abstract fun dataCaptureDao(): CaptureDao

    companion object {
        @Volatile
        private var INSTANCE: CaptureRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CaptureRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaptureRoomDatabase::class.java,
                    "data_capture_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

class CaptureRepository(private val captureDao: CaptureDao) {

    @WorkerThread
    suspend fun insert(
        mSensorName: String,
        xValue: Float,
        yValue: Float,
        zValue: Float,
        mCaptureCount: Int,
        uniqueID: Long,
        mDuration: Double
    ) {
        val mUniqueID: Long = uniqueID
        val captureEntity = CaptureEntity(
            mCaptureCount,
            mUniqueID,
            mSensorName,
            mDuration,
            "",
            mCaptureCount,
            xValue,
            yValue,
            zValue
        )

        captureDao.insert(captureEntity)
    }

    @WorkerThread
    suspend fun getDataCaptureList(): List<DataToAnalyseSummary> {
        val dataToAnalyseSummaryList: List<DataToAnalyseSummary> = captureDao.getDataCaptureList()
        return dataToAnalyseSummaryList
    }

    @WorkerThread
    fun getCaptureList(uniqueID: Long): Flow<List<CaptureEntity>> {
        val graphDataList: Flow<List<CaptureEntity>> = captureDao.getGraphData(uniqueID)
        return graphDataList
    }

}


class CaptureDBViewModel(private val repository: CaptureRepository) : ViewModel() {
    private val tag: String = "CaptureDBViewModel"
}

class CaptureDBViewModelFactory(private val repository: CaptureRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaptureDBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaptureDBViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}