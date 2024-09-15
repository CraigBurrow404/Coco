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
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseUIState
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.getSensorTypeName
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataCaptureViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataCaptureUiState())
    val uiState: StateFlow<DataCaptureUiState> = _uiState.asStateFlow()
    private var dataCaptureList: MutableList<DataCaptureUiState> = mutableListOf()
    val tag = "DataCaptureViewModel()"

    fun accuracyChanged() { }

    private fun updateUi(
        mCaptureCount: Int,
        timestamp: Long,
        mAccelX: Float,
        mAccelY: Float,
        mAccelZ: Float,
        mSampleRate: Int,
        mDuration: Double,
        mSensorListenerRegistered: Boolean
    ) {
        Log.v(tag, "updateUi called")

        val mSensorTimestamp = System.currentTimeMillis()
        val mSensorTimestampDate = Date(mSensorTimestamp)
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss.ss a ", Locale.getDefault())
        val mSensorTimestampDateFormatted = sdf.format(mSensorTimestampDate)

        _uiState.update { currentState ->
            currentState.copy(
                captureCount = mCaptureCount,
                timestamp = timestamp,
                formattedTimestamp =  mSensorTimestampDateFormatted,
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

        Log.v(tag, "DataCaptureViewModel sensorChanged Called")

        if (uiState.value.captureSensorData) {
            val mSensorName: String = event?.sensor?.stringType!!
            val mSensorType: Int? = event.sensor?.type
            val mSensorListenerRegistered: Boolean = uiState.value.mSensorListenerRegistered
            val mFirstCapture: Long = uiState.value.firstCapture
            var mCaptureCount = uiState.value.captureCount
            val mSensorTimestamp = System.currentTimeMillis()
            val mDurationLong: Long = (mSensorTimestamp - mFirstCapture)
            val mDurationDouble: Double = mDurationLong.toDouble()
            val mDurationSec: Double = (mDurationDouble / 1000) // duration is in Nano seconds
            val mCaptureCountDouble: Double = mCaptureCount.toDouble()
            val mSampleRate: Double = (mCaptureCountDouble / mDurationSec)
            val mSampleRateInt: Int = mSampleRate.toInt()
            val mSensorTimestampDate = Date(mSensorTimestamp)
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss.ss a ", Locale.getDefault())
            val mSensorTimestampDateFormatted = sdf.format(mSensorTimestampDate).toString()
            val mSensorValueSize = event.values?.size!!
            val xValue: Float = event.values?.get(0)!!
            var yValue = 0f
            var zValue = 0f

            if (mSensorValueSize == 3) {
                yValue = event.values?.get(1)!!
                zValue = event.values?.get(2)!!
            }

            // TODO Make this accept multi-picks of Sensors rather than Hardcoded
            //******* Check if data should be captured *******//
            when (mSensorType) {
                TYPE_ACCELEROMETER, TYPE_GYROSCOPE,
                TYPE_MAGNETIC_FIELD, TYPE_AMBIENT_TEMPERATURE,
                TYPE_GAME_ROTATION_VECTOR, TYPE_GRAVITY,
                TYPE_ROTATION_VECTOR -> {

                    checkIfSaveFirstCaptureTimestamp(mSensorTimestamp)

                    Log.v(tag,"Sensor type $mSensorType captured")
                    dataCaptureList.add(
                        DataCaptureUiState(
                            mFirstCapture,
                            mSensorName,
                            mDurationSec,
                            mSensorTimestamp,
                            mSensorTimestampDateFormatted,
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
                        mSensorTimestamp,
                        xValue,
                        yValue,
                        zValue,
                        mSampleRateInt,
                        mDurationSec,
                        mSensorListenerRegistered
                    )

                    mCaptureDBViewModel.insert(
                        mSensorName,
                        xValue,
                        yValue,
                        zValue,
                        mCaptureCount,
                        uiState.value.firstCapture, // firstCapture becomes the uniqueID
                        uiState.value.duration
                    )
                }
            }
        }
    }


    private fun checkIfSaveFirstCaptureTimestamp(timestamp: Long) {
        if (uiState.value.firstCapture == 0L) {
            _uiState.update { currentState ->
                currentState.copy(
                    firstCapture = timestamp,
                    uniqueId = timestamp // deliberately duplicated to track into AnalyseData
                )
            }
         }
    }

    private fun startCapture(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        registerSensorListener(
            mSensorManager,
            mSensorEventListener
        )
        _uiState.update { currentState ->
            currentState.copy(
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
                firstCapture = 0,
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
        analyseViewModel: AnalyseViewModel,
        navController: NavController,
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        var mDataCaptureButtonText = uiState.value.dataCaptureButtonText
        val mAnalyseUIState = AnalyseUIState(0, "", 0)

        when (mDataCaptureButtonText) {

            "Start" -> {
                viewModel.startCapture(
                    mSensorManager,
                    mSensorEventListener
                )
                mDataCaptureButtonText = "Stop"

            }

            "Stop" -> {
                viewModel.stopCapture(
                    mSensorManager,
                    mSensorEventListener
                )
                mAnalyseUIState.uniqueId = uiState.value.uniqueId
                mAnalyseUIState.sensorName = uiState.value.sensorName
                mAnalyseUIState.captureCount = uiState.value.captureCount
                analyseViewModel.setAnalyseUIState(mAnalyseUIState)
                mDataCaptureButtonText = "Analyse Data"
            }

            "Analyse Data" -> {
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
}