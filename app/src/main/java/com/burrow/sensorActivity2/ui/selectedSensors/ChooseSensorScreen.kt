package com.burrow.sensorActivity2.ui.selectedSensors

import android.hardware.Sensor
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

// TODO the UIs sole responsibility should be to consume and display UI state.

@Composable
fun ChooseSensorScreen(
    viewModel: ChooseSensorViewModel,
    navController: NavController,
    modifier: Modifier,
    mSensorList: MutableList<Sensor>,
    dataCaptureViewModel: DataCaptureViewModel
) {
    val tag = "ChooseSensorScreen"
    Log.v(tag, "Started with ${mSensorList.size} Sensors")
    val primaryButtonColor = setPrimaryButtonColor()
    val secondaryButtonColor = setSecondaryButtonColor()

   Column {

       Spacer(modifier = modifier.weight(0.15f))

       LazyColumn(
           modifier
               .fillMaxWidth()
               .weight(0.68f)
       ) {

           items(mSensorList)

           { sensor ->
               ChooseSensorCard(
                   sensor = sensor,
                   modifier = modifier,
                   onClick = {
                       viewModel.rememberSelectedSensor(sensor = sensor)
                       dataCaptureViewModel.setSelectedSensor(
                           mSelectedSensorType = sensor.type,
                           mSelectedSensorStringType = sensor.stringType
                           // TODO
                           //Button changes colour + adds Selected WRONG...
                           //The color should be maintained as part of the UI state and the
                           // viewModel called to update the UI State when the button is clicked
                           //As a result, you should never modify the UI state in the UI directly
                       // unless the UI itself is the sole source of its data. Violating this
                       // principle results in multiple sources of truth for the
                       // same piece of information, leading to data inconsistencies and subtle bugs.
                           // UI State is immutable in the UI Layer
                       )
                   }
               )
           }

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
                       // This should only happen if a sensor has been selected otherwise remind them with a toast
                       navController.navigate(route = SensorAppEnum.DataCaptureScreen.name)},
                   colors = primaryButtonColor
               ) {
                   Text(
                       fontSize = 32.sp,
                       text = stringResource(R.string.capture_data)
                   )
               }
           }
           Spacer(Modifier.weight(0.1f))
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
    //Log.v(TAG, "ChooseSensorScreen Ended")
}