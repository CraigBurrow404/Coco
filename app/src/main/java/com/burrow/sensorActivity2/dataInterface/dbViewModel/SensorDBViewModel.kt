package com.burrow.sensorActivity2.dataInterface.dbViewModel

import android.hardware.Sensor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.burrow.sensorActivity2.dataInterface.entity.SensorEntity
import com.burrow.sensorActivity2.dataInterface.repository.SensorRepository
import kotlinx.coroutines.launch

class SensorDBViewModel (private val mRepository: SensorRepository) : ViewModel() {

    fun insertSensorList(mSensorList: List<Sensor>) = viewModelScope.launch {
        //mRepository.deleteAll()
        var sensorListCount = 1

        for (item: Sensor in mSensorList) {

            val mSelectSensorRow = SensorEntity(
                sensorListCount,
                item.name,
                item.vendor,
                item.version,
                item.type,
                item.maximumRange,
                item.resolution,
                item.power,
                item.minDelay
            )

            mRepository.insert(mSelectSensorRow)

            sensorListCount++
        }
    }

}
class SelectSensorViewModelFactory(private val sensorRepository: SensorRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorDBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensorDBViewModel(sensorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}