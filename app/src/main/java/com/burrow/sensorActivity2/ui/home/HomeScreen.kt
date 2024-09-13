package com.burrow.sensorActivity2.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.common.setPrimaryButtonColor
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum
import com.burrow.sensorActivity2.ui.theme.backgroundDark
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onChooseSensorButtonClicked: () -> Unit,
    onAnalyseDataButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    onExitButtonClicked: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val TAG = "MyActivity"
    Log.v(TAG, "HomeScreen Started")

    val mainButtonColor = setPrimaryButtonColor()
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
            println("Choose Sensor Selected")
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
            println("Search Selected")
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
            println("Cancel Selected")
            Text(fontSize = 24.sp,
                text = stringResource(R.string.exit))
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val homeViewModel : HomeViewModel = viewModel()
    val navController : NavController = rememberNavController()

    HomeScreen(
        homeViewModel = homeViewModel,
        navController = navController,
        onChooseSensorButtonClicked = {
            navController.navigate(route = SensorAppEnum.ChooseSensorScreen.name)},
        onAnalyseDataButtonClicked = {
            navController.navigate(route = SensorAppEnum.AnalyseDataScreen.name)},
        onSearchButtonClicked = {
            navController.navigate(route = SensorAppEnum.SearchDataCaptureScreen.name)},
        onExitButtonClicked = {}
    )
}