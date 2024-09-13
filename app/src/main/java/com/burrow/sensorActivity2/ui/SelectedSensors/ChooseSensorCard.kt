package com.burrow.sensorActivity2.ui.SelectedSensors

import android.hardware.Sensor
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.ui.common.getSensorTypeName
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor

@Composable
fun ChooseSensorCard(
    modifier: Modifier = Modifier,
    sensor: Sensor,
    onClick: () -> Unit) {

    //val TAG = "MyActivity"
    //Log.v(TAG, "ChooseSensorCard Started")

    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Color.Blue else Color.Yellow

    val sensorTypeName = getSensorTypeName(sensor.stringType)

    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
    )
    {
        Spacer(modifier = Modifier.weight(0.1f)) // Move Button to Horizontal centre
        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.8f)
                .weight(0.8f),
            onClick = onClick,
            shape = RoundedCornerShape(4.dp),
            colors = tertiaryButtonColor,
        ) {
            Text(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.CenterVertically),
                text = sensorTypeName,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
    Spacer(modifier = Modifier.height(32.dp))
    //Log.v(TAG, "ChooseSensorCard Ended")
}