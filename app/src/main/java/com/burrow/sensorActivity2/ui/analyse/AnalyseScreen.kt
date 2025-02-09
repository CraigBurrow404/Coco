package com.burrow.sensorActivity2.ui.analyse

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum
import java.net.URI

@Composable
fun AnalyseScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalyseViewModel,
    navController: NavController,
    mFilePath: String,
    context: Context
) {

// TODO the UIs sole responsibility should be to consume and display UI state.

    val firstRowInList = 0
    val secondaryButtonColor = setSecondaryButtonColor()
    val mBatchId = viewModel.getBatchId()

    Column(modifier.background(color = MaterialTheme.colorScheme.background)) {
        Spacer(Modifier.weight(0.15f))
        Row(Modifier.weight(0.1f)) {
            Text(
                modifier = Modifier.weight(0.1f),
                text = "Data Values",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(Modifier.weight(0.3f)) {

            Column(Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.weight(0.1f),
                    text = viewModel.text,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            /*            LazyColumn(
                            Modifier
                                .fillMaxSize()
                        ) {
                            itemsIndexed(captureList.value)
                            { _, row ->
                                val captureEntity: CaptureEntity = row

                                AnalyseDataCard(
                                    onClick = {},
                                    batchId = captureEntity.batchId,
                                    firstCapture = Timestamp(captureEntity.firstCapture)
                                        .toString().substring(startIndex = 0, endIndex = 19)
                                )
                            }
                        }
              */
            // TODO Add more Analysis features as they become apparent
            //  XAxisGraph(modifier.weight(0.3f))
        }
        Row(Modifier.weight(0.1f)) {
            Spacer(modifier = Modifier.weight(0.1f))
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
                        Log.v(
                            "Analyse Screen",
                            "Batch IdZ ${viewModel.mCaptureList!![firstRowInList].batchId}" +
                                    " CaptureList.size : ${viewModel.mCaptureList!!.size}"
                        )
                        val fileURI: URI =
                            createCSV(
                                viewModel.mCaptureList!![firstRowInList].batchId,
                                viewModel.mCaptureList!!,
                                mFilePath
                            )
                        val fileUri: Uri = Uri.parse(
                            fileURI.toString()
                        )
                        Log.v(
                            "AnalyseDataScreen", "fileURI : $fileURI" +
                                    "fileUri : $fileUri"
                        )
                        viewModel.shareDataFile(context, mBatchId, viewModel.mCaptureList!!)
                    },
                    colors = secondaryButtonColor
                ) {
                    Text(
                        fontSize = 32.sp,
                        text = stringResource(R.string.share)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Row(Modifier.weight(0.1f)) { }
        Row(
            modifier = Modifier
                .weight(0.1f)
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(0.9f),
                    shape = RoundedCornerShape(4.dp),
                    onClick = { navController.navigate(route = SensorAppEnum.HomeScreen.name) },
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

