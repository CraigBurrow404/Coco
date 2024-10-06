package com.burrow.sensorActivity2.ui.analyse

import android.util.Log
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI

fun createCSV(batchID: Long,
              captureData: List<CaptureEntity>,
              mFilePath: String)
    : URI {

    //  /data/user/0/com.burrow.sensorActivity2/files
    val file = File("$mFilePath//Capture$batchID.csv")
    val URI : URI = file.toURI()
    Log.v("createCSV", "File : $file URI : ${URI.toString()}"
    )

    try {
        file.createNewFile()
    } catch (e : Exception) {
        Log.v("File Create Failed", e.toString())
    }
    val path = file.path
    Log.v("createCSV", path.toString())
    FileOutputStream(file).apply { writeCsv(captureData) }

    return URI
}

fun OutputStream.writeCsv(captureData: List<CaptureEntity>) {

    val writer = bufferedWriter()
    writer.write(
        """"UID ,",
       |"First Capture ,", 
       |"Batch Id ,",
       |"Timestamp ,",
       |"Sensor name ,",
       |"Duration ,",
       |"Sensitivity ,",
       |"Capture Count ,",
       |"X Value ,",
       |"Y Value ,",
       |"Z Value"
       |""".trimMargin()
    )
    writer.newLine()
    captureData.forEach {
        writer.write(
            " ${it.firstCapture}," +
                    " ${it.batchId}," +
                    " ${it.timestamp}," +
                    " ${it.sensorName}," +
                    " ${it.duration}," +
                    " ${it.sensitivity}," +
                    " ${it.captureCount}," +
                    " ${it.captureValueX}," +
                    " ${it.captureValueY}," +
                    " ${it.captureValueZ}"
        )
        writer.newLine()
    }
    writer.flush()
}
