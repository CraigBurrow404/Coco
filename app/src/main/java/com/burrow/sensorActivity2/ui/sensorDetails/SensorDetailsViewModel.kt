package com.burrow.sensorActivity2.ui.sensorDetails

import androidx.lifecycle.ViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SensorDetailsViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(
        SensorDetailsUiState(
            0,
            "",
            "",
            0,
            0,
            0F,
            0F,
            0F,
            0,
            0,
            false,
            "Select"
        )
    )

    val uiState: StateFlow<SensorDetailsUiState> = _uiState.asStateFlow()

    fun updateSensorDetailsUi(
        uid: Int,
        sensorName: String,
        sensorVendor: String,
        sensorVersion: Int,
        sensorType: Int,
        sensorMaxRange: Float,
        sensorResolution: Float,
        sensorPower: Float,
        sensorMinDelay: Int
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                uid = uid,
                sensorName = sensorName,
                sensorVendor = sensorVendor,
                sensorVersion = sensorVersion,
                sensorType = sensorType,
                sensorMaxRange = sensorMaxRange,
                sensorResolution = sensorResolution,
                sensorPower = sensorPower,
                sensorMinDelay = sensorMinDelay
            )
        }
    }

    fun getUIDTitle(): String {
        val mText = "Unique Identifier"
        return mText
    }

    fun getUID(): String {
        val mText = uiState.value.uid.toString()
        return mText
    }

    fun getSensorNameTitle(): String {
        val mText = "Sensor Name"
        return mText
    }

    fun getSensorName(): String {
        val mText = uiState.value.sensorName.toString()
        return mText
    }

    fun getSensorVendorTitle(): String {
        val mText = "Sensor Vendor"
        return mText
    }

    fun getSensorVendor(): String {
        val mText = uiState.value.sensorVendor.toString()
        return mText
    }

    fun getSensorVersionTitle(): String {
        val mText = "Sensor Version"
        return mText
    }

    fun getSensorVersion(): String {
        val mText = uiState.value.sensorVersion.toString()
        return mText
    }

    fun getSensorTypeTitle(): String {
        val mText = "Sensor Type"
        return mText
    }

    fun getSensorType(): String {
        val mText = uiState.value.sensorType.toString()
        return mText
    }

    fun getSensorMaxRangeTitle(): String {
        val mText = "Sensor Max Range"
        return mText
    }

    fun getSensorMaxRange(): String {
        val mText = uiState.value.sensorMaxRange.toString()
        return mText
    }

    fun getSensorResolutionTitle(): String {
        val mText = "Sensor Resolution"
        return mText
    }

    fun getSensorResolution(): String {
        val mString = "${String.format("%.8f", uiState.value.sensorResolution)}"
        return mString
    }

    fun getSensorPowerTitle(): String {
        val mText = "Sensor Power"
        return mText
    }

    fun getSensorPower(): String {
        val mText = uiState.value.sensorPower.toString()
        return mText
    }

    fun getSensorMinDelayTitle(): String {
        val mText = "Sensor Min Delay"
        return mText
    }

    fun getSensorMinDelay(): String {
        val mText = uiState.value.sensorMinDelay.toString()
        return mText
    }

    fun setSelectedSensor(selectedSensor: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSensor = selectedSensor
            )
        }
    }

    fun setSelectButtonText() {

        var mSelectSensorButtonText: String = "Tap to Select Sensor"

        if (uiState.value.selectedSensor == 99999999 || uiState.value.selectedSensor == 0) { // Sensor Selected
            mSelectSensorButtonText = "Deselect"
            setSelectedSensor(uiState.value.sensorType)
            setSensorSelected(true)
        } else { // Deselect Sensor
            setSelectedSensor(uiState.value.sensorType)
            mSelectSensorButtonText = "Select"
            setSelectedSensor(99999999)
            setSensorSelected(false)
        }

        _uiState.update { currentState ->
            currentState.copy(
                SelectSensorButtonText = mSelectSensorButtonText
            )
        }
    }

    fun setSensorSelected(mSensorSelected : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                SensorSelected = mSensorSelected
            )
        }
    }
}