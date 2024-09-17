package com.burrow.sensorActivity2.ui.selectedSensors

import android.hardware.Sensor
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.ui.common.getSensorTypeName
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor

@Composable
fun ChooseSensorCard(
    modifier: Modifier = Modifier,
    sensor: Sensor,
    onClick: () -> Unit) {

    val tag = "ChooseSensorCard"
    Log.v(tag, "")

    val tertiaryButtonColor = setTertiaryButtonColor()
    val sensorTypeName = getSensorTypeName(sensor.stringType)

    Row(
        modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
    )
    {
        Spacer(modifier = modifier.weight(0.1f)) // Move Button to Horizontal centre
        Button(
            modifier = modifier
                .height(64.dp)
                .fillMaxWidth(0.8f)
                .weight(0.8f),
            onClick = onClick,
            shape = RoundedCornerShape(4.dp),
            colors = tertiaryButtonColor,
        ) {
            Text(
                modifier = modifier.fillMaxSize()
                    .align(Alignment.CenterVertically),
                text = sensorTypeName,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
        }
        Spacer(modifier = modifier.weight(0.1f))
    }
    Spacer(modifier = modifier.height(32.dp))
}