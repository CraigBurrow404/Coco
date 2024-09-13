package com.burrow.sensorActivity2.ui.analyse

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.dataInterface.Entity.CaptureEntity
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.charts.XAxisGraph
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureCard
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyseScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalyseViewModel,
    mCaptureDBViewModel : CaptureDBViewModel,
    navController: NavController
) {


// TODO the UI's sole responsibility should be to consume and display UI state.

    val tag = "MyActivity"
    Log.v(tag, "Analyse Data Screen Started")

    val mainButtonColor = setPrimaryButtonColor()
    val secondButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()
    val uniqueID = viewModel.getUniqueID()

    val sensorName = viewModel.getSensorName()
    val captureCount = viewModel.getCaptureCount()
    var graphDataList : MutableList<CaptureEntity> =
        viewModel.getGraphDataList(uniqueID, mCaptureDBViewModel)

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
        Row(Modifier.weight(0.6f)) {
            graphDataList = viewModel.getGraphDataList(uniqueID, mCaptureDBViewModel)
            LazyColumn(
                Modifier
                    .fillMaxSize()
            ) {
                items(graphDataList.size)
                { dataListIndex ->
                    AnalyseDataCard(
                        modifier = Modifier,
                        onClick = {},
                        xValue = graphDataList[dataListIndex].captureValueX,
                        yValue = graphDataList[dataListIndex].captureValueY,
                        zValue = graphDataList[dataListIndex].captureValueZ
                    )
                }
            }
           // XAxisGraph(modifier.weight(0.3f))
        }
        Row(Modifier.weight(0.1f)) { }
        Row(Modifier.weight(0.1f)) { }
        Row(Modifier.weight(0.1f)) { }
    }
}