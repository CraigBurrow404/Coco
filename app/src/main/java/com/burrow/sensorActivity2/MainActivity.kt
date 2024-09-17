package com.burrow.sensorActivity2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModelProvider
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModel
import com.burrow.sensorActivity2.dataInterface.dbViewModel.CaptureDBViewModelFactory
import com.burrow.sensorActivity2.dataInterface.dbViewModel.SelectSensorViewModelFactory
import com.burrow.sensorActivity2.dataInterface.dbViewModel.SensorDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeViewModel
import com.burrow.sensorActivity2.ui.info.InfoViewModel
import com.burrow.sensorActivity2.ui.chooseDataToAnalyse.DataToAnalyseViewModel
import com.burrow.sensorActivity2.ui.selectData.SelectDataViewModel
import com.burrow.sensorActivity2.ui.selectedSensors.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.sensorApp.SensorApp
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel
import com.burrow.sensorActivity2.ui.theme.SensorAppTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDataCaptureViewModel: DataCaptureViewModel
    private lateinit var mSelectDataViewModel: SelectDataViewModel
    private lateinit var mAnalyseViewModel: AnalyseViewModel
    private lateinit var mSensorDetailsViewModel: SensorDetailsViewModel
    private lateinit var mSelectSensorViewModel: ChooseSensorViewModel
    private lateinit var mInfoViewModel: InfoViewModel
    private lateinit var mDataToAnalyseViewModel: DataToAnalyseViewModel

    private val captureDBViewModel by viewModels {
        CaptureDBViewModelFactory((application as SensorApplication).captureRepository)
    }

    private val mSensorDBViewModel: SensorDBViewModel by viewModels {
        SelectSensorViewModelFactory((application as SensorApplication).sensorRepository)
    }

    private lateinit var mSensorManager: SensorManager
    private val mSensorEventListener = this
    private lateinit var mSensorList: MutableList<Sensor>
    private var preferredSensorList: MutableList<Sensor> = mutableListOf()
    private val tag: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Log.v(tag, "MainActivity onCreate Started")

        // Set up Sensor Manager and register Listener
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class]
        mSelectSensorViewModel = ViewModelProvider(this)[ChooseSensorViewModel::class]
        mDataCaptureViewModel = ViewModelProvider(this)[DataCaptureViewModel::class]
        mSelectDataViewModel = ViewModelProvider(this)[SelectDataViewModel::class]
        mAnalyseViewModel = ViewModelProvider(this)[AnalyseViewModel::class]
        mSensorDetailsViewModel = ViewModelProvider(this)[SensorDetailsViewModel::class]
        mInfoViewModel = ViewModelProvider(this)[InfoViewModel::class]
        mDataToAnalyseViewModel = ViewModelProvider(this)[DataToAnalyseViewModel::class]

        //Grab the list of Available Sensors and insert it onto select_sensor_table
        val mSensorList = insertSensorList()

        //captureDBViewModel.deleteALL()
        Log.v(tag, "MainActivity onCreate setContent")

        setContent {
            SensorAppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                SensorApp(
                    mDataCaptureViewModel,
                    mSelectSensorViewModel,
                    mAnalyseViewModel,
                    mSensorDetailsViewModel,
                    mDataToAnalyseViewModel,
                    c,
                    mSensorManager,
                    mSensorEventListener,
                    mSensorList
                )
            }
        }

        Log.v(tag, "MainActivity onCreate Ended")
    }

    override fun onPause() {
        // Entered when user taps Back / Recent Buttons - Activity is out of focus but running
        // UI can be updated
        // do not use it to save data, make network calls, or execute DB transactions
        super.onPause()
        Log.v(tag, "MainActivity onPause Called")
        mDataCaptureViewModel.unregisterSensorListener(mSensorManager, mSensorEventListener)
    } // End of onPause()

    override fun onStop() {
        // Activity is no longer visible
        super.onStop()
        Log.v(tag,"MainActivity onStop Called")
    } // End of onStop()

    override fun onRestart() {
        // Activity moves from stopped to restarted - restores the state of the activity
        super.onRestart()
        Log.v(tag, "MainActivity onStop Called")
    } // End of onRestart()

    override fun onDestroy() {
        // ensures release of all activity resources and the processes containing them are destroyed
        Log.v(tag, "MainActivity onDestroy Called")
        super.onDestroy()
    } // End of onDestroy()

    override fun onSensorChanged(mSensorEvent: SensorEvent?) {
        Log.v(tag, "MainActivity onSensorChanged Called")
        mDataCaptureViewModel.sensorChanged(
            mSensorEvent,
            c
        )
    }

    override fun onAccuracyChanged(mSensor: Sensor?, mSensorAccuracyValue: Int) {
        Log.v(tag, "MainActivity onAccuracyChanged Called")
        mDataCaptureViewModel.accuracyChanged() // this needs an observable flow of data...
    }

    private fun insertSensorList(): MutableList<Sensor> {
        Log.v(tag, "MainActivity insertSensorList Called")
        mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL)
        mSensorDBViewModel.insertSensorList(mSensorList)
        preferredSensorList.clear()

        var index = 0

        while (index < mSensorList.size) {
            when (mSensorList[index].stringType) {
                Sensor.STRING_TYPE_ACCELEROMETER -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_GYROSCOPE -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_MAGNETIC_FIELD -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_AMBIENT_TEMPERATURE -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_GRAVITY -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_GAME_ROTATION_VECTOR -> preferredSensorList.add(mSensorList[index])
                Sensor.STRING_TYPE_ROTATION_VECTOR -> preferredSensorList.add(mSensorList[index])
                else -> {}
            }
            index++
        }
        return preferredSensorList
    }
}