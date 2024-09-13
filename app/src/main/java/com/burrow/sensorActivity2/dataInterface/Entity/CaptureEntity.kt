package com.burrow.sensorActivity2.dataInterface.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_capture_table")
data class CaptureEntity(
    @PrimaryKey val uid  : Int,
    @ColumnInfo(name = "unique_id") val UniqueID : Long,
    @ColumnInfo(name = "sensor_name") val sensorName: String,
    @ColumnInfo(name = "duration") val duration: Double,
    @ColumnInfo(name = "sensitivity") val sensitivity: String,
    @ColumnInfo(name = "capture_count") val captureCount: Int,
    @ColumnInfo(name = "capture_valueX") val captureValueX: Float,
    @ColumnInfo(name = "capture_valueY") val captureValueY: Float,
    @ColumnInfo(name = "capture_valueZ") val captureValueZ: Float
)