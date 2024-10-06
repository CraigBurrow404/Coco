package com.burrow.sensorActivity2.ui.captureHistory

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModel
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum
import java.sql.Timestamp

@Composable
fun CaptureHistoryScreen(
    viewModel: CaptureHistoryViewModel,
    navController: NavController,
    modifier: Modifier,
    captureDBViewModel: CaptureDBViewModel,
    analyseViewModel: AnalyseViewModel
) {

    val secondaryButtonColor = setSecondaryButtonColor()
    val mCaptureList: List<CaptureEntity>
            by captureDBViewModel.getBatchList().collectAsState(initial = emptyList())

    Column {

        Spacer(modifier = modifier.weight(0.15f))

        LazyColumn(
            modifier
                .fillMaxWidth()
                .weight(0.68f)
        ) {
            items(mCaptureList.size)
            { index ->
                CaptureHistoryCard(
                    viewModel = viewModel,
                    batchId = mCaptureList[index].batchId,
                    firstCapture = Timestamp(mCaptureList[index].firstCapture)
                        .toString().substring(startIndex = 0, endIndex = 19),
                    modifier = modifier,
                    onClick = {
                        viewModel.updateBatchId(
                            mCaptureList[index].batchId,
                            Timestamp(mCaptureList[index].firstCapture).toString()
                        )
                        analyseViewModel.setBatchId(mCaptureList[index].batchId)
                        Log.v(
                            "CaptureHistoryScreen", "Index : $index, BatchId :" +
                                    "${mCaptureList[index].batchId}"
                        )
                        navController.navigate(route = SensorAppEnum.AnalyseDataScreen.name)
                    }
                )
            }
        }
        Row(Modifier.weight(0.1f)) {
            Spacer(Modifier.weight(0.1f))

            Spacer(Modifier.weight(0.1f))
        }

        Spacer(Modifier.weight(0.02f))

        Row(Modifier.weight(0.1f)) {
            Spacer(Modifier.weight(0.1f))
            Column(
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