package com.burrow.sensorActivity2.ui.sensorApp

import androidx.annotation.StringRes
import com.burrow.sensorActivity2.R

enum class SensorAppEnum(@StringRes val title: Int) {
        HomeScreen(title = R.string.home_screen),
        ChooseSensorScreen(title = R.string.select_sensor),
        DataCaptureScreen(title = R.string.capture_data),
        AnalyseDataScreen(title = R.string.analyse_data),
        SelectDataScreen(title = R.string.select_data),
        SearchDataScreen(title = R.string.search_data_screen),
        SensorDetailsScreen(title = R.string.sensor_details),
        InitialLogInScreen(title = R.string.initial_login),
        LogInScreen(title = R.string.login),
        InfoScreen(title = R.string.info_screen),
        SearchDataCaptureScreen(title=R.string.choose_data_capture_screen)

}