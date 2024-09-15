package com.burrow.sensorActivity2.ui.dataCapture

import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel

@Composable
fun ActionButton(
    viewModel : DataCaptureViewModel,
    analyseViewModel: AnalyseViewModel,
    navController: NavController,
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    mActionButtonText : String,
    mainButtonColor : ButtonColors,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            shape = RoundedCornerShape(4.dp),
            onClick = {
                viewModel.handleButtonClick(
                    viewModel,
                    analyseViewModel,
                    navController,
                    mSensorManager,
                    mSensorEventListener
                )
            },
            colors = mainButtonColor,
            modifier = modifier.fillMaxHeight().fillMaxWidth(0.9f)


        ) {
            Text(
                fontSize = 48.sp,
                text = mActionButtonText,
                textAlign = TextAlign.Center,
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