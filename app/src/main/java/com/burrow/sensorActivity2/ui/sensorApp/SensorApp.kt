package com.burrow.sensorActivity2.ui.sensorApp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseScreen
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureScreen
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeScreen
import com.burrow.sensorActivity2.ui.info.InfoScreen
import com.burrow.sensorActivity2.ui.captureHistory.CaptureHistoryScreen
import com.burrow.sensorActivity2.ui.captureHistory.CaptureHistoryViewModel
import com.burrow.sensorActivity2.ui.selectData.SelectDataScreen
import com.burrow.sensorActivity2.ui.chooseSensors.ChooseSensorScreen
import com.burrow.sensorActivity2.ui.chooseSensors.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsScreen
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SensorApp(
    dataCaptureViewModel: DataCaptureViewModel = viewModel(),
    selectSensorViewModel: ChooseSensorViewModel = viewModel(),
    mAnalyseViewModel: AnalyseViewModel = viewModel(),
    mCaptureDBViewModel : CaptureDBViewModel = viewModel(),
    sensorDetailsViewModel: SensorDetailsViewModel = viewModel(),
    captureHistoryViewModel: CaptureHistoryViewModel = viewModel(),
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    mSensorList: MutableList<Sensor>,
    mFilePath : String,
    context : Context
) {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SensorAppEnum.valueOf(
        backStackEntry?.destination?.route ?: SensorAppEnum.HomeScreen.name
    )
    val activity = (context as? Activity)

    Scaffold(
        topBar = {
            SensorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navController = navController,
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SensorAppEnum.HomeScreen.name,
            modifier = Modifier
        ) {
            composable(route = SensorAppEnum.HomeScreen.name) {
                HomeScreen(
                    onChooseSensorButtonClicked = {
                        navController.navigate(route = SensorAppEnum.ChooseSensorScreen.name)},
                    onSearchButtonClicked = {
                        navController.navigate(route = SensorAppEnum.SearchDataCaptureScreen.name)},
                    onExitButtonClicked = {activity?.finish()}
                )
            }
            composable(route = SensorAppEnum.ChooseSensorScreen.name) {
                ChooseSensorScreen(
                    viewModel = selectSensorViewModel,
                    navController = navController,
                    mSensorList = mSensorList,
                    dataCaptureViewModel = dataCaptureViewModel,
                    modifier = Modifier
                )
            }
            composable(route = SensorAppEnum.SelectDataScreen.name) {SelectDataScreen()}
            composable(route = SensorAppEnum.DataCaptureScreen.name) {
                DataCaptureScreen(
                    viewModel = dataCaptureViewModel,
                    navController = navController,
                    mCaptureDBViewModel = mCaptureDBViewModel,
                    mAnalyseViewModel = mAnalyseViewModel,
                    mSensorManager = mSensorManager,
                    mSensorEventListener = mSensorEventListener,
                    modifier = Modifier
                )
            }
            composable(route = SensorAppEnum.AnalyseDataScreen.name) {
                AnalyseScreen(
                    viewModel = mAnalyseViewModel,
                    navController = navController,
                    mFilePath = mFilePath,
                    context = context
                )
            }
            composable(route = SensorAppEnum.SensorDetailsScreen.name
            ) {
                SensorDetailsScreen(
                    viewModel = sensorDetailsViewModel
                )
            }
            composable(route = SensorAppEnum.InfoScreen.name
            ) {
                InfoScreen()
            }
            composable(route = SensorAppEnum.SearchDataCaptureScreen.name
            ) {
                CaptureHistoryScreen(
                    viewModel = captureHistoryViewModel,
                    navController = navController,
                    captureDBViewModel = mCaptureDBViewModel,
                    analyseViewModel = mAnalyseViewModel,
                    modifier = Modifier
                )
            }
        }
    }
}