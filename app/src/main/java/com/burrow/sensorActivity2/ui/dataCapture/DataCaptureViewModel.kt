package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE
import android.hardware.Sensor.TYPE_GAME_ROTATION_VECTOR
import android.hardware.Sensor.TYPE_GRAVITY
import android.hardware.Sensor.TYPE_GYROSCOPE
import android.hardware.Sensor.TYPE_MAGNETIC_FIELD
import android.hardware.Sensor.TYPE_ROTATION_VECTOR
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModel
import com.burrow.sensorActivity2.dataInterface.database.CaptureData
import com.burrow.sensorActivity2.ui.analyse.AnalyseUIState
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.getSensorTypeName
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DataCaptureViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataCaptureUiState())
    val uiState: StateFlow<DataCaptureUiState> = _uiState.asStateFlow()
    private var dataCaptureList: MutableList<DataCaptureUiState> = mutableListOf()

    fun accuracyChanged() { }

    private fun updateUi(
        mCaptureCount: Int,
        mFirstCapture: Long,
        timestamp: Long,
        mAccelX: Float,
        mAccelY: Float,
        mAccelZ: Float,
        mSampleRate: Int,
        mDuration: Double,
        mSensorListenerRegistered: Boolean
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                captureCount = mCaptureCount,
                firstCapture = mFirstCapture,
                timestamp = timestamp,
                captureValueX = mAccelX,
                captureValueY = mAccelY,
                captureValueZ = mAccelZ,
                captureRateHz = mSampleRate,
                duration = mDuration,
                mSensorListenerRegistered = mSensorListenerRegistered,
            )
        }
    }

    fun sensorChanged(
        event: SensorEvent?,
        mCaptureDBViewModel: CaptureDBViewModel
    ) {
        if (uiState.value.captureSensorData) {

            var mFirstCapture : Long = uiState.value.firstCapture

            if (mFirstCapture == 0L) {
                // Set the FirstCapture on the first Pass to teh current timestamp
                mFirstCapture = System.currentTimeMillis()
            }

            val mSensorName: String = event?.sensor?.stringType!!
            val mSensorType: Int? = event.sensor?.type
            val mSensorListenerRegistered: Boolean = uiState.value.mSensorListenerRegistered
            val mBatchId: Long = uiState.value.batchId
            Log.v("sensorChanged","mBatchId : $mBatchId")
            var mCaptureCount = uiState.value.captureCount
            val mSensorTimestamp = System.currentTimeMillis()
            val mDurationLong: Long = (mSensorTimestamp - mFirstCapture)
            val mDurationDouble: Double = mDurationLong.toDouble()
            val mDurationSec: Double = (mDurationDouble / 1000) // duration is in Nano seconds
            val mCaptureCountDouble: Double = mCaptureCount.toDouble()
            val mSampleRate: Double = (mCaptureCountDouble / mDurationSec)
            val mSampleRateInt: Int = mSampleRate.toInt()
            val mSensorValueSize = event.values?.size!!
            val xValue: Float = event.values?.get(0)!!
            var yValue = 0f
            var zValue = 0f

            if (mSensorValueSize == 3) {
                yValue = event.values?.get(1)!!
                zValue = event.values?.get(2)!!
            }

            //******* Check if data should be captured *******//
            when (mSensorType) {
                TYPE_ACCELEROMETER, TYPE_GYROSCOPE,
                TYPE_MAGNETIC_FIELD, TYPE_AMBIENT_TEMPERATURE,
                TYPE_GAME_ROTATION_VECTOR, TYPE_GRAVITY,
                TYPE_ROTATION_VECTOR -> {

                    checkIfSaveFirstCaptureTimestamp(mBatchId, mSensorTimestamp)

                    dataCaptureList.add(
                        DataCaptureUiState(
                            mBatchId,
                            mFirstCapture,
                            mSensorName,
                            mDurationSec,
                            mSensorTimestamp,
                            "",
                            true,
                            mCaptureCount,
                            xValue,
                            yValue,
                            zValue
                        )
                    )

                    mCaptureCount++

                    updateUi(
                        mCaptureCount,
                        mFirstCapture,
                        mSensorTimestamp,
                        xValue,
                        yValue,
                        zValue,
                        mSampleRateInt,
                        mDurationSec,
                        mSensorListenerRegistered
                    )
                    val mCaptureData = CaptureData(
                        batchId = uiState.value.batchId,
                        firstCapture = uiState.value.firstCapture,
                        timestamp = uiState.value.timestamp,
                        sensorName = mSensorName,
                        duration = uiState.value.duration,
                        sensitivity = uiState.value.sensitivity,
                        captureCount = mCaptureCount,
                        captureValueX = xValue,
                        captureValueY = yValue,
                        captureValueZ = zValue
                    )
                    mCaptureDBViewModel.insert(mCaptureData)
                }
            }
        }
    }


    private fun checkIfSaveFirstCaptureTimestamp(batchId: Long, timestamp: Long) {
        if (uiState.value.batchId == 0L) {
            _uiState.update { currentState ->
                currentState.copy(
                    batchId = batchId,
                    timestamp = timestamp // deliberately duplicated to track into AnalyseData
                )
            }
         }
    }

    private fun startCapture(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener,
        mCaptureDBViewModel: CaptureDBViewModel
    ) {
        val mBatchId = mCaptureDBViewModel.getNewBatchId()
        Log.v("StartCapture","mBatchId : $mBatchId")
        registerSensorListener(
            mSensorManager,
            mSensorEventListener
        )
        _uiState.update { currentState ->
            currentState.copy(
                batchId = mBatchId,
                captureSensorData = true,
                mSensorListenerRegistered = true
            )

        }
    }

    fun stopCapture(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        unregisterSensorListener(
            mSensorManager,
            mSensorEventListener
        )
        _uiState.update { currentState ->
            currentState.copy(
                captureSensorData = false,
                mSensorListenerRegistered = false
            )
        }
    }

    private fun registerSensorListener(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        //TODO Tidy this up to set Sensor managers for each Sensor Chosen from the pick list
        // - Not part of MVP

        //val mSensor: Sensor? = mSensorManager.getDefaultSensor(uiState.value.selectedSensor)
        val mSensor1: Sensor? = mSensorManager.getDefaultSensor(TYPE_ACCELEROMETER)
        val mSensor2: Sensor? = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE)
        val mSensor3: Sensor? = mSensorManager.getDefaultSensor(TYPE_MAGNETIC_FIELD)
        val mSensor4: Sensor? = mSensorManager.getDefaultSensor(TYPE_AMBIENT_TEMPERATURE)
        val mSensor5: Sensor? = mSensorManager.getDefaultSensor(TYPE_GRAVITY)
        val mSensor6: Sensor? = mSensorManager.getDefaultSensor(TYPE_GAME_ROTATION_VECTOR)
        val mSensor7: Sensor? = mSensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR)
        mSensorManager.registerListener(mSensorEventListener, mSensor1, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor2, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor3, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor4, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor5, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor6, 5000)
        mSensorManager.registerListener(mSensorEventListener, mSensor7, 5000)
    }

    fun unregisterSensorListener(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        mSensorManager.unregisterListener(mSensorEventListener)
    }

    fun setSelectedSensor(  // Tidies and abbreviates Sensor name for display on screen
        mSelectedSensorType: Int,
        mSelectedSensorStringType: String
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSensor = mSelectedSensorType,
                sensorName = getSensorTypeName(mSelectedSensorStringType)
            )
        }
    }

    fun clearPrevUIState() {
        _uiState.update { currentState ->
            currentState.copy(
                sensorName = "",
                duration = 0.0,
                timestamp = 0,
                sensitivity = "",
                captureSensorData = false,
                captureCount = 0,
                captureValueX = 0f,
                captureValueY = 0f,
                captureValueZ = 0f,
                dataCaptureButtonText = "Start",
                captureRateHz = 0,
            )
        }
    }

    fun handleButtonClick(
        viewModel: DataCaptureViewModel,
        mCaptureDBViewModel :CaptureDBViewModel,
        mAnalyseViewModel : AnalyseViewModel,
        navController: NavController,
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        var mDataCaptureButtonText = uiState.value.dataCaptureButtonText
        when (mDataCaptureButtonText) {

            "Start" -> {
                viewModel.startCapture(
                    mSensorManager,
                    mSensorEventListener,
                    mCaptureDBViewModel
                )
                mDataCaptureButtonText = "Stop"

            }

            "Stop" -> {
                viewModel.stopCapture(
                    mSensorManager,
                    mSensorEventListener
                )
                mDataCaptureButtonText = "Analyse Data"
            }

            "Analyse Data" -> {
                mAnalyseViewModel.setBatchId(uiState.value.batchId)
                navController.navigate(
                    route = SensorAppEnum.AnalyseDataScreen.name
                ) {
                    popUpTo(
                        route = SensorAppEnum.HomeScreen.name
                    )
                }

                clearPrevUIState()
                mDataCaptureButtonText = "Start"
            }

            else -> {
                mDataCaptureButtonText = "Start"
            }
        }

        _uiState.update { currentState ->
            currentState.copy(
                dataCaptureButtonText = mDataCaptureButtonText
            )
        }
    }

    fun deleteAll( mCaptureDBViewModel: CaptureDBViewModel ) {
        mCaptureDBViewModel.deleteAll()
    }

    fun getNewBatchId( mCaptureDBViewModel: CaptureDBViewModel) {
        mCaptureDBViewModel.getNewBatchId()
    }
}