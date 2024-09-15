package com.burrow.sensorActivity2.ui.searchDataCapture

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseUIState
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@Composable
fun SearchDataCaptureScreen(
    viewModel: SearchDataCaptureViewModel,
    navController: NavController,
    modifier: Modifier,
    analyseViewModel: AnalyseViewModel,
    captureDBViewModel: CaptureDBViewModel
) {

// TODO the UIs sole responsibility should be to consume and display UI state.

    val tag = "MyActivity"
    Log.v(tag, "SearchDataCaptureScreen Started")
    val secondaryButtonColor = setSecondaryButtonColor()
    val mDataCaptureSummaryList = viewModel.getDataCaptureSummaryList(captureDBViewModel)

    Column {

        Spacer(modifier = modifier.weight(0.15f))

        LazyColumn(
            modifier
                .fillMaxWidth()
                .weight(0.68f)
        ) {
            items(mDataCaptureSummaryList.size)
            { dataCaptureSummaryIndex ->
                SearchDataCaptureCard(
                    viewModel = viewModel,
                    modifier = modifier,
                    onClick = {
                        viewModel.rememberSelectedDataCapture(
                            mDataCaptureSummaryList[dataCaptureSummaryIndex])
                        mDataCaptureSummaryList[dataCaptureSummaryIndex].captureCount
                        val mAnalyseUIState = AnalyseUIState (
                            uniqueId = mDataCaptureSummaryList[dataCaptureSummaryIndex].uniqueID,
                            sensorName = mDataCaptureSummaryList[dataCaptureSummaryIndex].sensorName,
                            captureCount = mDataCaptureSummaryList[dataCaptureSummaryIndex].captureCount
                        )
                        Log.v(tag,"mAnalyseUIState $mAnalyseUIState")
                        analyseViewModel.setAnalyseUIState(mAnalyseUIState)
                        navController.navigate(route = SensorAppEnum.AnalyseDataScreen.name)
                    }
                )
            }
        }

       Spacer(Modifier.weight(0.02f))

       Row(Modifier.weight(0.1f)) {
           Spacer(Modifier.weight(0.1f))
           Column (
               Modifier
                   .fillMaxSize()
                   .weight(0.8f)
           ) {
               Button(
                   shape = RoundedCornerShape(4.dp),
                   modifier = Modifier
                       .fillMaxSize(),
                   onClick = {
                       navController.navigate(route = SensorAppEnum.HomeScreen.name)
                   },
                   colors = secondaryButtonColor
               ) {
                   Text(
                       fontSize = 32.sp,
                       text = stringResource(R.string.cancel)
                   )
               }
           }
           Spacer(Modifier.weight(0.1f))
       }

       Spacer(Modifier.weight(0.1f))
   }
}