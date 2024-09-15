package com.burrow.sensorActivity2.ui.sensorDetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class SensorDetailsViewModel : ViewModel() {

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

    fun getSensorVendorTitle(): String {
        val mText = "Sensor Vendor"
        return mText
    }

    fun getSensorVendor(): String {
        val mText = uiState.value.sensorVendor
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
        val mString = String.format(Locale.getDefault(),"%.8f", uiState.value.sensorResolution)
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

    private fun setSelectedSensor(selectedSensor: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSensor = selectedSensor
            )
        }
    }

    fun setSelectButtonText() {

        val mSelectSensorButtonText: String

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
                selectSensorButtonText = mSelectSensorButtonText
            )
        }
    }

    private fun setSensorSelected(mSensorSelected : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                sensorSelected = mSensorSelected
            )
        }
    }
}