package com.burrow.sensorActivity2.dataInterface.database

import androidx.datastore.preferences.protobuf.Timestamp
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_capture_table")
data class CaptureEntity(
    @PrimaryKey (autoGenerate = true) val uid : Int,
    val batchId : Long,
    val firstCapture : Long,
    val timestamp: Long,
    val sensorName: String,
    val duration: Double,
    val sensitivity: String,
    val captureCount: Int,
    val captureValueX: Float,
    val captureValueY: Float,
    val captureValueZ: Float
)