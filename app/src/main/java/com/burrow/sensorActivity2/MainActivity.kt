package com.burrow.sensorActivity2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.dbViewModel.SensorDBViewModel
import com.burrow.sensorActivity2.dataInterface.dbViewModel.SelectSensorViewModelFactory
import com.burrow.sensorActivity2.ui.Info.InfoViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.SelectedSensors.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.SelectedSensors.SelectDataViewModel
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModelFactory
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeViewModel
import com.burrow.sensorActivity2.ui.searchDataCapture.DataCaptureSummary
import com.burrow.sensorActivity2.ui.searchDataCapture.SearchDataCaptureViewModel
import com.burrow.sensorActivity2.ui.sensorApp.SensorApp
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel
import com.burrow.sensorActivity2.ui.theme.SensorAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), SensorEventListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDataCaptureViewModel: DataCaptureViewModel
    private lateinit var mSelectDataViewModel: SelectDataViewModel
    private lateinit var mAnalyseViewModel: AnalyseViewModel
    private lateinit var mSensorDetailsViewModel: SensorDetailsViewModel
    private lateinit var mSelectSensorViewModel: ChooseSensorViewModel
    private lateinit var mInfoViewModel: InfoViewModel
    private lateinit var mSearchDataCaptureViewModel: SearchDataCaptureViewModel

    private val mCaptureDBViewModel: CaptureDBViewModel by viewModels {
        CaptureDBViewModelFactory((application as SensorApplication).captureRepository)
    }

    private val mSensorDBViewModel: SensorDBViewModel by viewModels {
        SelectSensorViewModelFactory((application as SensorApplication).sensorRepository)
    }

    private lateinit var mSensorManager: SensorManager
    private val mSensorEventListener = this
    private lateinit var mSensorList: MutableList<Sensor>
    private var preferredSensorList: MutableList<Sensor> = mutableListOf()
    private val TAG: String = "MyActivity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Log.v(TAG, "MainActivity onCreate Started")

        // Set up Sensor Manager and register Listener
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class]
        mSelectSensorViewModel = ViewModelProvider(this)[ChooseSensorViewModel::class]
        mDataCaptureViewModel = ViewModelProvider(this)[DataCaptureViewModel::class]
        mSelectDataViewModel = ViewModelProvider(this)[SelectDataViewModel::class]
        mAnalyseViewModel = ViewModelProvider(this)[AnalyseViewModel::class]
        mSensorDetailsViewModel = ViewModelProvider(this)[SensorDetailsViewModel::class]
        mInfoViewModel = ViewModelProvider(this)[InfoViewModel::class]
        mSearchDataCaptureViewModel = ViewModelProvider(this)[SearchDataCaptureViewModel::class]

        //Grab the list of Available Sensors and insert it onto select_sensor_table
        val mSensorList = insertSensorList()

        //mCaptureDBViewModel.deleteALL()
        Log.v(TAG, "MainActivity onCreate setContent")

        setContent {
            SensorAppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                SensorApp(
                    mHomeViewModel,
                    mDataCaptureViewModel,
                    mSelectSensorViewModel,
                    mSelectDataViewModel,
                    mAnalyseViewModel,
                    mSensorDetailsViewModel,
                    mSensorDBViewModel,
                    mInfoViewModel,
                    mSearchDataCaptureViewModel,
                    mCaptureDBViewModel,
                    mSensorManager,
                    mSensorEventListener,
                    mSensorList
                )
            }
        }
        Log.v(TAG, "MainActivity onCreate Ended")
    }

    override fun onPause() {
        // Entered when user taps Back / Recent Buttons - Activity is out of focus but running
        // UI can be updated
        // do not use it to save data, make network calls, or execute DB transactions
        super.onPause()
        Log.v(TAG, "MainActivity onPause Called")
        mDataCaptureViewModel.unregisterSensorListener(mSensorManager, mSensorEventListener)
    } // End of onPause()

    override fun onStop() {
        // Activity is no longer visible
        super.onStop()
        println("MainActivity onStop Called")
    } // End of onStop()

    override fun onRestart() {
        // Activity moves from stopped to restarted - restores the state of the activity
        super.onRestart()
        Log.v(TAG, "MainActivity onStop Called")
    } // End of onRestart()

    override fun onDestroy() {
        // ensures release of all activity resources and the processes containing them are destroyed
        Log.v(TAG, "MainActivity onDestroy Called")
        super.onDestroy()
    } // End of onDestroy()

    override fun onSensorChanged(mSensorEvent: SensorEvent?) {
        Log.v(TAG, "MainActivity onSensorChanged Called")
        mDataCaptureViewModel.sensorChanged(
            mSensorEvent,
            mCaptureDBViewModel
        )
    }

    override fun onAccuracyChanged(mSensor: Sensor?, mSensorAccuracyValue: Int) {
        Log.v(TAG, "MainActivity onAccuracyChanged Called")
        mDataCaptureViewModel.accuracyChanged() // this needs an observable flow of data...
    }

    private fun insertSensorList(): MutableList<Sensor> {
        Log.v(TAG, "MainActivity insertSensorList Called")
        mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL)
        mSensorDBViewModel.insertSensorList(mSensorList)
        preferredSensorList.clear()

        var index: Int = 0

        while (index < mSensorList.size) {
            when (mSensorList[index].stringType) {
                Sensor.STRING_TYPE_ACCELEROMETER
                   -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_GYROSCOPE
                    -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_MAGNETIC_FIELD
                    -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_AMBIENT_TEMPERATURE
                    -> preferredSensorList.add(mSensorList[index])
                else -> {}
            }
            index++
        }
        return preferredSensorList
    }
}