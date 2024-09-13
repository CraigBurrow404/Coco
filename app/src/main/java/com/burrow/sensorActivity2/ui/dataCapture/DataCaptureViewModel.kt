package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.Sensor
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

class DataCaptureViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(DataCaptureUiState())
    val uiState: StateFlow<DataCaptureUiState> = _uiState.asStateFlow()
    private var dataCaptureList: MutableList<DataCaptureUiState> = mutableListOf()
    val TAG = "MyActivity"
    fun accuracyChanged() { }

    private fun updateUi(
        mCaptureCount: Int,
        timestamp: Long,
        formattedTimestamp: String,
        mAccelX: Float,
        mAccelY: Float,
        mAccelZ: Float,
        mSampleRate: Int,
        mDuration: Double,
        mSensorListenerRegistered: Boolean
    ) {
        //******* Calculate Current Timestamp in correct Format *******//

        Log.v(TAG, "DataCaptureViewModel updateUi called")

        val mSensorTimestamp = System.currentTimeMillis()
        val mSensorTimestampDate = Date(mSensorTimestamp)
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss.sssss a ")
        val mSensorTimestampDateFormatted = sdf.format(mSensorTimestampDate)

        _uiState.update { currentState ->
            currentState.copy(
                captureCount = mCaptureCount,
                timestamp = timestamp,
                formattedTimestamp =  mSensorTimestampDateFormatted,
                captureValueX = mAccelX,
                captureValueY = mAccelY,
                captureValueZ = mAccelZ,
                CaptureRateHz = mSampleRate,
                duration = mDuration,
                mSensorListenerRegistered = mSensorListenerRegistered,
            )
        }
    }

    fun sensorChanged(
        event: SensorEvent?,
        mCaptureDBViewModel: CaptureDBViewModel
    ) {

        Log.v(TAG, "DataCaptureViewModel sensorChanged Called")

        val mSensorName: String = event?.sensor?.stringType!!
        var mSensorIsWakeUp = event.sensor.isWakeUpSensor
        var mSensorIsDynamic = event.sensor.isDynamicSensor
        var mSensorId = event.sensor.id
        var mSensorFifoMaxEventCount = event.sensor.fifoMaxEventCount
        var mSensorFifoReservedEventCount = event.sensor.fifoReservedEventCount
        var mSensorIsAdditionalInfo = event.sensor.isAdditionalInfoSupported
        var mSensorMaxDelay = event.sensor.maxDelay
        var mSensorMinDelay = event.sensor.minDelay
        var mSensorMaxRange = event.sensor.maximumRange
        var mSensorPower = event.sensor.power
        var mSensorReportingMode = event.sensor.reportingMode
        var mSensorResolution = event.sensor.resolution
        var mSensorStringType = event.sensor.stringType
        var mSensorVendor = event.sensor.vendor
        var mSensorVersion = event.sensor.version
        var mSensorIndices = event.values.indices
        var mSensorlastIndex = event.values.lastIndex
        var mSensorAccuracy = event.accuracy.toString()

        val mSensorType: Int? = event?.sensor?.type
        val mSelectedSensor = uiState.value.selectedSensor

        val mSensorListenerRegistered: Boolean = uiState.value.mSensorListenerRegistered

        val mFirstCapture: Long = uiState.value.firstCapture

        //******** Calculate Duration and Sample Rate ************//

        var mCaptureCount = uiState.value.captureCount
        val mSensorTimestamp = System.currentTimeMillis()
        val mDurationLong: Long = (mSensorTimestamp - mFirstCapture)
        val mDurationDouble: Double = mDurationLong.toDouble()
        val mDurationSec: Double = (mDurationDouble / 1000) // duration is in Nano seconds
        val mCaptureCountDouble: Double = mCaptureCount.toDouble()
        val mSampleRate: Double = (mCaptureCountDouble / mDurationSec)
        val mSampleRateInt: Int = mSampleRate.toInt()
        val mSensorTimestampDate = Date(mSensorTimestamp)
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss.sssss a ")
        val mSensorTimestampDateFormatted = sdf.format(mSensorTimestampDate)

        //******* Sensors have different value sizes ********//
        val mSensorValueSize = event.values?.size!!
        val xValue: Float = event.values?.get(0)!!
        var yValue: Float = 0f
        var zValue: Float = 0f

        if (mSensorValueSize == 3) {
            yValue = event.values?.get(1)!!
            zValue = event.values?.get(2)!!
        }

        //******* Check if data should be captured *******//
        if (mSensorType == mSelectedSensor //I am not sure this is ever false
            && uiState.value.captureSensorData
        ) {
            checkIfSaveFirstCaptureTimestamp(mSensorTimestamp)

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
                mSensorTimestampDateFormatted,
                xValue,
                yValue,
                zValue,
                mSampleRateInt,
                mDurationSec,
                mSensorListenerRegistered
            )
        }

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

    fun startCapture(
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
        val mSensor: Sensor? = mSensorManager.getDefaultSensor(uiState.value.selectedSensor)
        mSensorManager.registerListener(mSensorEventListener, mSensor, 10000)
    }

    fun unregisterSensorListener(
        mSensorManager: SensorManager,
        mSensorEventListener: SensorEventListener
    ) {
        mSensorManager.unregisterListener(mSensorEventListener)
    }

    fun setSelectedSensor(
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

     fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss.sss")
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm.sss")
        return df.parse(date).time
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
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
                CaptureRateHz = 0,
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
        var mAnalyseUIState = AnalyseUIState(0,"",0 )

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