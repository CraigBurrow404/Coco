package com.burrow.sensorActivity2.ui.dataCapture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun CaptureRateValue( mCaptureRateHz: Int, modifier: Modifier ) {
    Box( modifier = modifier, contentAlignment = Alignment.CenterEnd ) {
        Text(
            modifier = modifier,
            fontSize = 32.sp,
            text = "${mCaptureRateHz}"
        )
    }
}