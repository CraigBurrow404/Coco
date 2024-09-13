package com.burrow.sensorActivity2.ui.common

import java.util.Locale

fun getSensorTypeName(sensorTypeString : String): String {

    val maxTextLengthForButton = 14
    val sensorTypeNameDelimited: String = sensorTypeString.substringAfterLast(delimiter = ".")
    val sensorTypeNameUpperCase: String = sensorTypeNameDelimited
        .replaceFirstChar {
            if (it. isLowerCase()) it. titlecase(Locale. getDefault()) else it. toString() }
    var sensorTypeNameShortened: String = sensorTypeNameUpperCase
    if (sensorTypeNameShortened.length > maxTextLengthForButton) {
        sensorTypeNameShortened = sensorTypeNameShortened.substring(0, maxTextLengthForButton)
    }
    val sensorTypeStringName: String = sensorTypeNameShortened.replace("_"," ")

    //***************** Tidy Second Words ********************//
    val sensorTypeStringNameTidy1 : String =
        sensorTypeStringName.replace("temper", "Temp")

    val sensorTypeStringNameTidy2 : String =
        sensorTypeStringNameTidy1.replace("field", "Field")

    val sensorTypeStringNameTidy3 : String =
        sensorTypeStringNameTidy2.replace("rotation", "Rotation")

        val sensorTypeStringNameTidy4 : String =
        sensorTypeStringNameTidy3.replace(" ro", "")

    val sensorTypeStringNameTidy5 : String =
        sensorTypeStringNameTidy4.replace("acceler", "Accel")

    val sensorTypeStringNameTidy6 : String =
        sensorTypeStringNameTidy5.replace("vecto", "Vect")

    val sensorTypeStringNameTidy7 : String =
        sensorTypeStringNameTidy6.replace("humid", "Humid")

    val sensorTypeStringNameTidy8 : String =
        sensorTypeStringNameTidy7.replace("sensor", "Sensor")

    val sensorTypeStringNameTidy9 : String =
        sensorTypeStringNameTidy8.replace("detector", "Detector")

    val sensorTypeStringNameTidy10 : String =
        sensorTypeStringNameTidy9.replace("counter", "Counter")

    val sensorTypeStringNameTidy11 : String =
        sensorTypeStringNameTidy10.replace(" temp", " Temp")

    return sensorTypeStringNameTidy11
}