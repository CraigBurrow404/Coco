package com.burrow.sensorActivity2.dataInterface.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "select_sensor_table")
data class SensorEntity(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "sensor_name") var sensorName: String,
    @ColumnInfo(name = "sensor_vendor") var sensorVendor: String,
    @ColumnInfo(name = "sensor_version") var sensorVersion: Int,
    @ColumnInfo(name = "sensor_type") var sensorType: Int,
    @ColumnInfo(name = "sensor_max_range") var sensorMaxRange: Float,
    @ColumnInfo(name = "sensor_resolution") var sensorResolution: Float,
    @ColumnInfo(name = "sensor_power") var sensorPower: Float,
    @ColumnInfo(name = "sensor_min_delay") var sensorMinDelay: Int
)