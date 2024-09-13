package com.burrow.sensorActivity2.ui.sensorApp

import android.annotation.SuppressLint
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.burrow.sensorActivity2.dataInterface.dbViewModel.SensorDBViewModel
import com.burrow.sensorActivity2.ui.Info.InfoScreen
import com.burrow.sensorActivity2.ui.Info.InfoViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseScreen
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.chooseSensor.ChooseSensorScreen
import com.burrow.sensorActivity2.ui.chooseSensor.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.chooseSensor.SelectDataScreen
import com.burrow.sensorActivity2.ui.chooseSensor.SelectDataViewModel
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureScreen
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeScreen
import com.burrow.sensorActivity2.ui.home.HomeViewModel
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureScreen
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureViewModel
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsScreen
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SensorApp(
    homeViewModel: HomeViewModel = viewModel(),
    dataCaptureViewModel: DataCaptureViewModel = viewModel(),
    selectSensorViewModel: ChooseSensorViewModel = viewModel(),
    selectDataViewModel: SelectDataViewModel = viewModel(),
    analyseViewModel: AnalyseViewModel = viewModel(),
    sensorDetailsViewModel: SensorDetailsViewModel = viewModel(),
    mSensorDBViewModel: SensorDBViewModel = viewModel(),
    infoViewModel: InfoViewModel = viewModel(),
    searchDataCaptureViewModel: SearchDataCaptureViewModel = viewModel(),
    captureDBViewModel: CaptureDBViewModel = viewModel(),
    mSensorManager: SensorManager,
    mSensorEventListener: SensorEventListener,
    mSensorList: MutableList<Sensor>
) {
    val TAG = "MyActivity"
    Log.v(TAG, "SensorApp Started")

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SensorAppEnum.valueOf(
        backStackEntry?.destination?.route ?: SensorAppEnum.HomeScreen.name
    )
    val activity = (LocalContext.current as? Activity)


    Scaffold(
        topBar = {
            Log.v(TAG, "SensorApp SensorAppBar Called")
            SensorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navController = navController,
            )
        }
    ) {
        //Log.v(TAG, "SensorApp NavHost Started")

        NavHost(
            navController = navController,
            startDestination = SensorAppEnum.HomeScreen.name,
            modifier = Modifier
        ) {
            composable(route = SensorAppEnum.HomeScreen.name) {
                Log.v(TAG, "SensorApp HomeScreen called by NavHost")
                HomeScreen(
                    homeViewModel = homeViewModel,
                    navController = navController,
                    onChooseSensorButtonClicked = {
                        navController.navigate(route = SensorAppEnum.ChooseSensorScreen.name)},
                    onAnalyseDataButtonClicked = {
                        navController.navigate(route = SensorAppEnum.AnalyseDataScreen.name)},
                    onSearchButtonClicked = {
                        navController.navigate(route = SensorAppEnum.SearchDataCaptureScreen.name)},
                    onExitButtonClicked = {activity?.finish()}
                )
            }
            composable(route = SensorAppEnum.ChooseSensorScreen.name) {
                Log.v(TAG, "SensorApp ChooseSensorScreen called by NavHost")
                ChooseSensorScreen(
                    viewModel = selectSensorViewModel,
                    navController = navController,
                    mSensorList = mSensorList,
                    dataCaptureViewModel = dataCaptureViewModel,
                    mSensorManager = mSensorManager
                )
            }
            composable(route = SensorAppEnum.SelectDataScreen.name) {
                Log.v(TAG, "SensorApp SelectDataScreen called by NavHost")
                SelectDataScreen(viewModel = selectDataViewModel,navController = navController)
            }
            composable(route = SensorAppEnum.DataCaptureScreen.name) {
                Log.v(TAG, "SensorApp DataCaptureScreen called by NavHost")
                DataCaptureScreen(
                    viewModel = dataCaptureViewModel,
                    navController = navController,
                    analyseViewModel = analyseViewModel,
                    mSensorManager = mSensorManager,
                    mSensorEventListener = mSensorEventListener
                )
            }
            composable(route = SensorAppEnum.AnalyseDataScreen.name) {
                Log.v(TAG, "SensorApp AnalyseScreen called by NavHost")
                AnalyseScreen(
                    viewModel = analyseViewModel,
                    mCaptureDBViewModel = captureDBViewModel,
                    navController = navController
                )
            }
            composable(route = SensorAppEnum.SensorDetailsScreen.name
            ) {
                Log.v(TAG, "SensorApp SelectDetailsScreen called by NavHost")
                SensorDetailsScreen(
                    viewModel = sensorDetailsViewModel,
                    navController = navController,
                )
            }
            composable(route = SensorAppEnum.InfoScreen.name
            ) {
                Log.v(TAG, "SensorApp InfoScreenv called by NavHost")
                InfoScreen(
                    infoViewModel = infoViewModel,
                    navController = navController
                )
            }
            composable(route = SensorAppEnum.SearchDataCaptureScreen.name
            ) {
                Log.v(TAG, "SensorApp SearchDataCaptureScreen called by NavHost")
                SearchDataCaptureScreen(
                    viewModel = searchDataCaptureViewModel,
                    navController = navController,
                    analyseViewModel = analyseViewModel,
                    captureDBViewModel = captureDBViewModel
                )
            }
        }
    }
    //Log.v(TAG, "SensorApp Ended")
}