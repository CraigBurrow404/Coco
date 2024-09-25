package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import java.util.Locale

@Composable
fun DataCaptureScreen(
    viewModel: DataCaptureViewModel,
    mCaptureDBViewModel: CaptureDBViewModel,
    navController: NavController,
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    modifier: Modifier
) {

    val uiState by viewModel.uiState.collectAsState()
    val mCaptureCount = uiState.captureCount
    val mainButtonColor = setPrimaryButtonColor()
    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()
    val mCaptureRateHz = uiState.captureRateHz
    val mDurationSec = String.format(Locale.getDefault(), "%.2f", uiState.duration)
    val mActionButtonText = uiState.dataCaptureButtonText

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Column(modifier = modifier.fillMaxSize()) {

            Spacer(modifier = modifier.weight(0.15f))

            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.1f)
            ) {
                Spacer(modifier = modifier.weight(0.051f))
                DataCaptureTitle(tertiaryButtonColor, modifier.weight(0.9f))
                Spacer(modifier = modifier.weight(0.05f))
            }

            Row(modifier = modifier.weight(0.1f)) {
                Spacer(modifier.weight(0.05f))
                DataCaptureCountTitle(modifier.weight(.2f))
                DataCaptureCountValue(mCaptureCount, modifier.weight(0.2f))
                Spacer(modifier.weight(0.1f))
                DataCaptureRateTitle(modifier.weight(0.2f))
                DataCaptureRateValue(mCaptureRateHz, modifier.weight(0.2f))
                Spacer(modifier = modifier.weight(0.05f))
            }

            Row(modifier = modifier.weight(0.1f)) {
                Spacer(modifier = modifier.weight(0.05f))
                DataCaptureDurationTitle(modifier.weight(0.15f))
                DataCaptureDurationValue(modifier.weight(0.25f), mDurationSec)
                Spacer(modifier = modifier.weight(0.55f))
            }

            Row(modifier = modifier
                .weight(0.3f)
                .align(Alignment.CenterHorizontally)) {
                Spacer(modifier = modifier.weight(0.1f))
                DataCaptureActionButton(
                    viewModel,
                    mCaptureDBViewModel,
                    navController,
                    mSensorManager,
                    mSensorEventListener,
                    mActionButtonText,
                    mainButtonColor,
                    modifier = modifier.weight(0.8f)
                )
                Spacer(modifier = modifier.weight(0.1f))
            }

            Spacer(modifier = modifier.weight(0.04f))

            Row(modifier = modifier
                .weight(0.1f)
                .align(Alignment.CenterHorizontally)) {
                DataCaptureCancelButton(
                    viewModel,
                    mSensorManager,
                    mSensorEventListener,
                    navController,
                    secondaryButtonColor,
                    modifier = modifier
                )
            }

            Spacer(modifier = modifier.weight(0.1f))
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