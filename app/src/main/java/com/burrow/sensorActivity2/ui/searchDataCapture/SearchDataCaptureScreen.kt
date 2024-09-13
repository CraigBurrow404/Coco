package com.burrow.sensorActivity2.ui.searchDataCapture

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.analyse.AnalyseUIState
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDataCaptureScreen(
    viewModel: SearchDataCaptureViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    analyseViewModel: AnalyseViewModel,
    captureDBViewModel: CaptureDBViewModel
) {


// TODO the UI's sole responsibility should be to consume and display UI state.

    val TAG = "MyActivity"
    Log.v(TAG, "SearchDataCaptureScreen Started")

    val mainButtonColor = setPrimaryButtonColor()
    val primaryButtonColor = setSecondaryButtonColor()
    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()

    val mDataCaptureSummaryList = viewModel.getDataCaptureSummaryList(captureDBViewModel)
    val (value, onValueChange) = remember { mutableStateOf("") }

    Column() {

        Spacer(modifier = Modifier.weight(0.15f))

        TextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            label = { Text(text = "Enter Date") },
            placeholder = { Text(text = "25/12/24") },
            textStyle = TextStyle(fontSize = 24.sp),
            modifier = Modifier
                .background(color = Color.DarkGray)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.colors(contentColorFor(backgroundColor = Color.Red))
        )

        Spacer(modifier = Modifier.weight(0.05f))

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(0.68f)
        ) {
            items(mDataCaptureSummaryList.size)
            { dataCaptureSummaryIndex ->
                SearchDataCaptureCard(
                    viewModel = viewModel,
                    modifier = Modifier,
                    onClick = {
                        viewModel.rememberSelectedDataCapture(
                            mDataCaptureSummaryList[dataCaptureSummaryIndex])
                        mDataCaptureSummaryList[dataCaptureSummaryIndex].capture_count
                        val mAnalyseUIState = AnalyseUIState (
                            uniqueId = mDataCaptureSummaryList[dataCaptureSummaryIndex].unique_id,
                            sensorName = mDataCaptureSummaryList[dataCaptureSummaryIndex].sensor_name,
                            captureCount = mDataCaptureSummaryList[dataCaptureSummaryIndex].capture_count
                        )
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