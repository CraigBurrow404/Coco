package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@Composable
fun CancelButton(
    viewModel: DataCaptureViewModel,
    mSensorManager : SensorManager,
    mSensorEventListener : SensorEventListener,
    navController : NavController,
    secondaryButtonColor : ButtonColors,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier.fillMaxSize(0.9f),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                viewModel.stopCapture(
                    mSensorManager,
                    mSensorEventListener
                )
                viewModel.clearPrevUIState()
                navController.navigate(route = SensorAppEnum.HomeScreen.name)
            },
            colors = secondaryButtonColor
        ) {
            Text(
                fontSize = 24.sp,
                text = stringResource(R.string.cancel),
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 1.5.em,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                )
            )
        }
    }
}