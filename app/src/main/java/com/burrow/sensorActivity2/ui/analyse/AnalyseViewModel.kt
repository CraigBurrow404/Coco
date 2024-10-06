package com.burrow.sensorActivity2.ui.analyse

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.burrow.sensorActivity2.SensorApplication
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.database.CaptureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


// TODO - The Data List should be updated in the UI state using this approach

class AnalyseViewModel(
    private val captureRepository: CaptureRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyseUIState())

    fun getCaptureData(mBatchId: Long)
            : Flow<List<CaptureEntity>> = captureRepository.getCaptureData(mBatchId)

    fun getBatchId(): Long {
        Log.v("AnalyseViewModel", "getBatchId UIState BatchId ${_uiState.value.batchId}")
        return _uiState.value.batchId
    }

    fun setBatchId(mBatchId: Long) {

        _uiState.update { currentState ->
            currentState.copy(
                batchId = mBatchId
            )
        }
        Log.v(
            "AnalyseViewModel Post Update", "mBatchId : $mBatchId," +
                    " UIState BatchId ${_uiState.value.batchId}"
        )

    }

    fun shareDataFile(context: Context, mBatchId: Long, mCaptureData: List<CaptureEntity>) {

        val file = File("${context.filesDir}Capture${mBatchId}.csv")
        Log.v("AnalyseViewModel", "File Created : $file ")
        FileOutputStream(file).apply { writeCsv(mCaptureData) }

        Intent(Intent.ACTION_CREATE_DOCUMENT).also {
            it.addCategory(Intent.CATEGORY_OPENABLE)
            it.type = "text/csv"
            it.putExtra(Intent.EXTRA_TITLE, file)
            if (it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
            }
        }
    }

    fun OutputStream.writeCsv(captureData: List<CaptureEntity>) {

        val writer = bufferedWriter()
        writer.write(
            """"UID ,",
       |"First Capture ,", 
       |"Batch Id ,",
       |"Timestamp ,",
       |"Sensor name ,",
       |"Duration ,",
       |"Sensitivity ,",
       |"Capture Count ,",
       |"X Value ,",
       |"Y Value ,",
       |"Z Value"
       |""".trimMargin()
        )
        writer.newLine()
        captureData.forEach {
            writer.write(
                " ${it.firstCapture}," +
                        " ${it.batchId}," +
                        " ${it.timestamp}," +
                        " ${it.sensorName}," +
                        " ${it.duration}," +
                        " ${it.sensitivity}," +
                        " ${it.captureCount}," +
                        " ${it.captureValueX}," +
                        " ${it.captureValueY}," +
                        " ${it.captureValueZ}"
            )
            writer.newLine()
        }
        writer.flush()
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return AnalyseViewModel(
                    (application as SensorApplication).captureRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
