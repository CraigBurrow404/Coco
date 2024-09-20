package com.burrow.sensorActivity2.ui.analyse

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@Composable
fun AnalyseScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalyseViewModel,
    navController: NavController
) {

// TODO the UIs sole responsibility should be to consume and display UI state.

    val tag = "AnalyseScreen"
    Log.v(tag, "Screen Started")

    val secondaryButtonColor = setSecondaryButtonColor()
    val uniqueID = viewModel.getUniqueID()
    val captureList : State<List<CaptureEntity>> =
        viewModel.getCaptureList(uniqueID).collectAsState(initial = emptyList())

    Column(modifier.background(color = MaterialTheme.colorScheme.background)) {
        Spacer(Modifier.weight(0.1f))
        Row(Modifier.weight(0.1f)) {
            Text(
                modifier = Modifier.weight(0.1f),
                text = "Data Values",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(Modifier.weight(0.3f)) {

            LazyColumn(
                Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(captureList.value)
                { _, row ->
                    val captureEntity: CaptureEntity = row
                    AnalyseDataCard(
                        onClick = {},
                        xValue = captureEntity.captureValueX,
                        yValue = captureEntity.captureValueY,
                        zValue = captureEntity.captureValueZ
                    )
                }
            }
        // TODO Add more Analysis features as they become apparent
        //  XAxisGraph(modifier.weight(0.3f))
        }
        Row(Modifier.weight(0.1f)) { }
        Row(Modifier.weight(0.1f)) { }
        Row(modifier = Modifier.weight(0.1f).align(Alignment.CenterHorizontally)) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(0.9f),
                    shape = RoundedCornerShape(4.dp),
                    onClick = {navController.navigate(route = SensorAppEnum.HomeScreen.name) },
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
        Spacer(Modifier.weight(0.1f))
    }
}