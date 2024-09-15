package com.burrow.sensorActivity2.ui.sensorDetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor


@Composable
fun SensorDetailsScreen(
    viewModel: SensorDetailsViewModel,
    modifier: Modifier = Modifier
) {

// TODO the UIs sole responsibility should be to consume and display UI state.

    val tag = "SensorDetailsScreen"
    Log.v(tag, "Started")
    val uiState by viewModel.uiState.collectAsState()
    val secondaryButtonColor = setSecondaryButtonColor()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Spacer(modifier = Modifier.height(144.dp))

            Row {
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = viewModel.getSensorTypeTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorType(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorVendorTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorVendor(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorPowerTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorPower(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorVersionTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorVersion(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorMaxRangeTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorMaxRange(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorMinDelayTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorMinDelay(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorResolutionTitle(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = viewModel.getSensorResolution(),
                    modifier = Modifier.weight(0.4f),
                    fontSize = 24.sp
                )
            }

            Row(modifier = Modifier.weight(0.10f)) {
                Spacer(modifier = Modifier.weight(0.1f))
                if (uiState.sensorSelected) {
                    Text(
                        modifier = Modifier.weight(0.3f),
                        text = "Sensor Selected",
                        fontSize = 24.sp,
                        color = Color.LightGray
                    )
                } else {
                    Text(
                        modifier = Modifier.weight(0.3f),
                        text = "Sensor Not Selected",
                        fontSize = 24.sp,
                        color = Color.Red
                    )
                }
            }

            Row(modifier = Modifier.weight(0.1f)) {

                Spacer(modifier = Modifier.weight(0.1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(0.8f)
                        .height(64.dp),
                    shape = RoundedCornerShape (4.dp),
                    onClick = {
                        viewModel.setSelectButtonText()
                    } ,
                        colors = secondaryButtonColor
                ) {
                    Text(
                        fontSize = 24.sp,
                        text = (uiState.selectSensorButtonText),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(0.15f))
            }

            Row(modifier = Modifier.weight(0.1f)) {

                Spacer(modifier = Modifier.weight(0.1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(0.8f)
                        .height(64.dp),
                    shape = RoundedCornerShape (4.dp),
                    onClick = {
                        viewModel.setSelectButtonText()
                    } ,
                    colors = secondaryButtonColor
                ) {
                    Text(
                        fontSize = 24.sp,
                        text = (stringResource(id = R.string.cancel)),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(0.15f))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}