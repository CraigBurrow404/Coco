package com.burrow.sensorActivity2.ui.sensorApp

import android.annotation.SuppressLint
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseScreen
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureScreen
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeScreen
import com.burrow.sensorActivity2.ui.info.InfoScreen
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureScreen
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureViewModel
import com.burrow.sensorActivity2.ui.selectData.SelectDataScreen
import com.burrow.sensorActivity2.ui.selectedSensors.ChooseSensorScreen
import com.burrow.sensorActivity2.ui.selectedSensors.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsScreen
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SensorApp(
    dataCaptureViewModel: DataCaptureViewModel = viewModel(),
    selectSensorViewModel: ChooseSensorViewModel = viewModel(),
    analyseViewModel: AnalyseViewModel = viewModel(),
    sensorDetailsViewModel: SensorDetailsViewModel = viewModel(),
    searchDataCaptureViewModel: SearchDataCaptureViewModel = viewModel(),
    captureDBViewModel: CaptureDBViewModel = viewModel(),
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    mSensorList: MutableList<Sensor>
) {
    val tag = "MyActivity"
    Log.v(tag, "SensorApp Started")

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SensorAppEnum.valueOf(
        backStackEntry?.destination?.route ?: SensorAppEnum.HomeScreen.name
    )
    val activity = (LocalContext.current as? Activity)


    Scaffold(
        topBar = {
            Log.v(tag, "SensorApp SensorAppBar Called")
            SensorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navController = navController,
            )
        }
    ) {
        //Log.v(tag, "SensorApp NavHost Started")

        NavHost(
            navController = navController,
            startDestination = SensorAppEnum.HomeScreen.name,
            modifier = Modifier
        ) {
            composable(route = SensorAppEnum.HomeScreen.name) {
                Log.v(tag, "SensorApp HomeScreen called by NavHost")
                HomeScreen(
                    onChooseSensorButtonClicked = {
                        navController.navigate(route = SensorAppEnum.ChooseSensorScreen.name)},
                    onSearchButtonClicked = {
                        navController.navigate(route = SensorAppEnum.SearchDataCaptureScreen.name)},
                    onExitButtonClicked = {activity?.finish()}
                )
            }
            composable(route = SensorAppEnum.ChooseSensorScreen.name) {
                Log.v(tag, "SensorApp ChooseSensorScreen called by NavHost")
                ChooseSensorScreen(
                    viewModel = selectSensorViewModel,
                    navController = navController,
                    mSensorList = mSensorList,
                    dataCaptureViewModel = dataCaptureViewModel,
                    modifier = Modifier
                )
            }
            composable(route = SensorAppEnum.SelectDataScreen.name) {
                Log.v(tag, "SensorApp SelectDataScreen called by NavHost")
                SelectDataScreen()
            }
            composable(route = SensorAppEnum.DataCaptureScreen.name) {
                Log.v(tag, "SensorApp DataCaptureScreen called by NavHost")
                DataCaptureScreen(
                    viewModel = dataCaptureViewModel,
                    navController = navController,
                    analyseViewModel = analyseViewModel,
                    mSensorManager = mSensorManager,
                    mSensorEventListener = mSensorEventListener,
                    modifier = Modifier
                )
            }
            composable(route = SensorAppEnum.AnalyseDataScreen.name) {
                Log.v(tag, "SensorApp AnalyseScreen called by NavHost")
                AnalyseScreen(
                    viewModel = analyseViewModel,
                    mCaptureDBViewModel = captureDBViewModel,
                    navController = navController
                )
            }
            composable(route = SensorAppEnum.SensorDetailsScreen.name
            ) {
                Log.v(tag, "SensorApp SelectDetailsScreen called by NavHost")
                SensorDetailsScreen(
                    viewModel = sensorDetailsViewModel
                )
            }
            composable(route = SensorAppEnum.InfoScreen.name
            ) {
                Log.v(tag, "SensorApp InfoScreen called by NavHost")
                InfoScreen()
            }
            composable(route = SensorAppEnum.SearchDataCaptureScreen.name
            ) {
                Log.v(tag, "SensorApp SearchDataCaptureScreen called by NavHost")
                SearchDataCaptureScreen(
                    viewModel = searchDataCaptureViewModel,
                    navController = navController,
                    analyseViewModel = analyseViewModel,
                    captureDBViewModel = captureDBViewModel,
                    modifier = Modifier
                )
            }
        }
    }
    //Log.v(tag, "SensorApp Ended")
}