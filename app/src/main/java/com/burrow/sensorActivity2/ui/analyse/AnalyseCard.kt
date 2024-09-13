package com.burrow.sensorActivity2.ui.analyse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureUiState

@Composable
fun AnalyseCard(
    modifier: Modifier = Modifier,
    dataCapture: DataCaptureUiState,
    onClick: () -> Unit
) {

    val captureCount = dataCapture.captureCount
    val timestamp = dataCapture.timestamp.toString()
    val x = dataCapture.captureValueX.toString()
    val y = dataCapture.captureValueY.toString()
    val z = dataCapture.captureValueZ.toString()

    Row(
        Modifier
            .clickable(onClick = onClick)
    )
    {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {

            Text(text = "captureCount: $captureCount, timestamp: $timestamp, X: $x, Y: $y, Z: $z",fontSize = 16.sp)
        }
    }
}