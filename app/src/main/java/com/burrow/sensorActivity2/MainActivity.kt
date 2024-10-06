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
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModel
import com.burrow.sensorActivity2.dataInterface.database.CaptureDBViewModelFactory
import com.burrow.sensorActivity2.dataInterface.database.SelectSensorViewModelFactory
import com.burrow.sensorActivity2.dataInterface.database.SensorDBViewModel
import com.burrow.sensorActivity2.ui.analyse.AnalyseViewModel
import com.burrow.sensorActivity2.ui.captureHistory.CaptureHistoryViewModel
import com.burrow.sensorActivity2.ui.chooseSensors.ChooseSensorViewModel
import com.burrow.sensorActivity2.ui.dataCapture.DataCaptureViewModel
import com.burrow.sensorActivity2.ui.home.HomeViewModel
import com.burrow.sensorActivity2.ui.info.InfoViewModel
import com.burrow.sensorActivity2.ui.selectData.SelectDataViewModel
import com.burrow.sensorActivity2.ui.sensorApp.SensorApp
import com.burrow.sensorActivity2.ui.sensorDetails.SensorDetailsViewModel
import com.burrow.sensorActivity2.ui.theme.SensorAppTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDataCaptureViewModel: DataCaptureViewModel
    private lateinit var mSelectDataViewModel: SelectDataViewModel
    private lateinit var mSensorDetailsViewModel: SensorDetailsViewModel
    private lateinit var mSelectSensorViewModel: ChooseSensorViewModel
    private lateinit var mInfoViewModel: InfoViewModel
    private lateinit var mCaptureHistoryViewModel: CaptureHistoryViewModel

    private val captureDBViewModel : CaptureDBViewModel by viewModels {
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
        mSensorDetailsViewModel = ViewModelProvider(this)[SensorDetailsViewModel::class]
        mInfoViewModel = ViewModelProvider(this)[InfoViewModel::class]
        mCaptureHistoryViewModel = ViewModelProvider(this)[CaptureHistoryViewModel::class]
        val mFilePath = this.filesDir.path

        val mAnalyseViewModel: AnalyseViewModel by viewModels { AnalyseViewModel.Factory }
        val context : Context = this

        //Grab the list of Available Sensors and insert it onto select_sensor_table
        val mSensorList = insertSensorList()

        setContent {
            SensorAppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                SensorApp(
                    mDataCaptureViewModel,
                    mSelectSensorViewModel,
                    mAnalyseViewModel,
                    captureDBViewModel,
                    mSensorDetailsViewModel,
                    mCaptureHistoryViewModel,
                    mSensorManager,
                    mSensorEventListener,
                    mSensorList,
                    mFilePath,
                    context
                )
            }
        }
        val filesDir = filesDir
        Log.v("MainActivity", filesDir.toString())

    }

    override fun onPause() {
        // Entered when user taps Back / Recent Buttons - Activity is out of focus but running
        // UI can be updated
        // do not use it to save data, make network calls, or execute DB transactions
        super.onPause()
        mDataCaptureViewModel.unregisterSensorListener(mSensorManager, mSensorEventListener)
    } // End of onPause()

    override fun onSensorChanged(mSensorEvent: SensorEvent?) {
        mDataCaptureViewModel.sensorChanged(
            mSensorEvent,
            captureDBViewModel
        )
    }

    override fun onAccuracyChanged(mSensor: Sensor?, mSensorAccuracyValue: Int) {
        mDataCaptureViewModel.accuracyChanged() // this needs an observable flow of data...
    }

    private fun insertSensorList(): MutableList<Sensor> {
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