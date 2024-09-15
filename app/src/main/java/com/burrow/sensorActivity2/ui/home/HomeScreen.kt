package com.burrow.sensorActivity2.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import com.burrow.sensorActivity2.ui.theme.backgroundDark

@Composable
fun HomeScreen(
    onChooseSensorButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    onExitButtonClicked: () -> Unit,
) {
    val tag = "HomeScreen"
    Log.v(tag, "HomeScreen")

    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(color = backgroundDark),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(144.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(64.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = onChooseSensorButtonClicked,
            colors = tertiaryButtonColor
        ) {
            Log.v(tag,"Choose Sensor Selected")
            Text(
                fontSize = 24.sp,
                text = stringResource(R.string.capture_data))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(64.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = onSearchButtonClicked,
            colors = tertiaryButtonColor
        ) {
            Log.v(tag,"Search Selected")
            Text(fontSize = 24.sp,
                text = stringResource(R.string.choose_data_capture))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(64.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = onExitButtonClicked,
            colors = secondaryButtonColor
        ) {
            Log.v(tag,"Cancel Selected")
            Text(fontSize = 24.sp,
                text = stringResource(R.string.exit))
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}