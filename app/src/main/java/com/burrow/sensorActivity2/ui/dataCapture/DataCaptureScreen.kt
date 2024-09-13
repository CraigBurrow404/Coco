package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor

@Composable
fun DataCaptureScreen(
    viewModel: DataCaptureViewModel,
    navController: NavController,
    analyseViewModel: AnalyseViewModel,
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener
) {


// TODO the UI's sole responsibility should be to consume and display UI state.

    val TAG = "MyActivity"
    Log.v(TAG, "DataCaptureScreen Started")

    val uiState by viewModel.uiState.collectAsState()
    val mCaptureCount = uiState.captureCount
    val mainButtonColor = setPrimaryButtonColor()
    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()

    val mCaptureRateHz = uiState.CaptureRateHz
    val mDurationSec = String.format("%.2f", uiState.duration)
    val mActionButtonText = uiState.dataCaptureButtonText

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.weight(0.1f))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.1f)
            ) {
                Spacer(modifier = Modifier.weight(0.051f))
                DataCaptureTitle(viewModel, tertiaryButtonColor, uiState,Modifier.weight(0.9f))
                Spacer(modifier = Modifier.weight(0.05f))
            }

            Row(modifier = Modifier.weight(0.1f)) {
                Spacer(Modifier.weight(0.05f))
                CaptureCountTitle(Modifier.weight(.2f))
                CaptureCountValue(mCaptureCount, Modifier.weight(0.2f))
                Spacer(Modifier.weight(0.1f))
                CaptureRateTitle(Modifier.weight(0.2f))
                CaptureRateValue(mCaptureRateHz, Modifier.weight(0.2f))
                Spacer(modifier = Modifier.weight(0.05f))
            }

            Row(modifier = Modifier.weight(0.1f)) {
                Spacer(modifier = Modifier.weight(0.05f))
                CaptureDurationTitle(Modifier.weight(0.15f))
                CaptureDurationValue(Modifier.weight(0.25f), mDurationSec)
                Spacer(modifier = Modifier.weight(0.55f))
            }

            Row(modifier = Modifier.weight(0.3f).align(Alignment.CenterHorizontally)) {
                Spacer(modifier = Modifier.weight(0.1f))
                ActionButton(
                    viewModel,
                    analyseViewModel,
                    navController,
                    mSensorManager,
                    mSensorEventListener,
                    mActionButtonText,
                    mainButtonColor,
                    modifier = Modifier.weight(0.8f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }

            Spacer(modifier = Modifier.weight(0.04f))

            Row(modifier = Modifier.weight(0.1f).align(Alignment.CenterHorizontally)) {
               CancelButton(
                   modifier = Modifier,
                   viewModel,
                   mSensorManager,
                   mSensorEventListener,
                   navController,
                   secondaryButtonColor
               )
            }

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

/*
@Preview
@Composable
fun DataCaptureScreenPreview() {
    DataCaptureScreen(
        viewModel = viewModel(),
        navController = NavController(LocalContext.current),
        analyseDataViewModel = viewModel(),
        mSensorManager = mSensorManager,
        mSensorEventListener = SensorEventListener
    )
}

 */