package com.burrow.sensorActivity2.ui.analyse

import com.burrow.sensorActivity2.dataInterface.database.CaptureEntity
import kotlin.math.sqrt

/*
    ##
    I don't think this works because it is looking purely at the acceleration value and does not
    take into account the existing speed - TODO needs testing

    I suspect it is ok while decelerating or accelerating only - because the values are squared
    removing any negative values

    However the likelihood of any object being measured by this being able to produce a zero change
    and therefore no reading is very very low - TODO needs testing

    It may be that the calculation need to know the current speed and add this into the distance
    travelled?
    ##

    Explanation of code to average the capture values and obtain distance and average speed values
    ----------------------------------------------------------------------------------------------
  */
/**
 *  This code takes the X,Y and Z values from the accelerometre and uses them to approximate time
 *  and distance travelled
 *
 *  To smooth out the calculations are completed using the average of the last 10 values for each
 *  of the X, Y and Z value readings
 *
 *  The duration is used to calculate the distance travelled speed of that average
 *
 *  To calculate the last ten values average it rolls through the entire Capture List in the below
 *  for loop
 *
 *  For the first ten rows all it does is build up the average values
 *
 *  After that it subtracts the oldest of the 10 rows and adds in the new value
 *  Before dividing that sum by 10
 *
 *  It then squares the X, Y and Z average values individually
 *  then it adds the Square X,Y and Z values together
 *  then it takes the Square root of that number
 *
 *  This is equal to the Magnitude of the vector from the Readings
 *
 *  It then Multiples this vectors magnitude by the duration of this reading
 *  Giving an approximation of the distance travelled
 *
 *  which may need the current known speed added to it

 */

fun calculateValues(mCaptureList: List<CaptureEntity>): CalculatedValues {

    var index = 0
    var tenRowsBackInList: Int
    var oneRowBackInList: Int
    var sumLatest10XValues = 0.0F
    var sumLatest10YValues = 0.0F
    var sumLatest10ZValues = 0.0F
    var oldestRowIn10XValues: Float
    var oldestRowIn10YValues: Float
    var oldestRowIn10ZValues: Float
    var xValueSquared: Float
    var yValueSquared: Float
    var zValueSquared: Float
    var sumOfSquaredValues: Float
    var squareRootofValues: Float
    var currentDuration: Double
    var currentDistanceTravelled: Double
    var totalDistanceTravelled = 0.0
    var mAverageSpeed = 0.0
    var metreClicker = 0.0
    var tenMetreClicker = 0.0
    val mTimeToEachMetre = mutableListOf(0.0)
    val mTimeToEachTenMetres = mutableListOf(0.0)
    val mCalculatedValues : CalculatedValues
    val firstRowInCaptureList = 0
    val lastRowInCaptureList = mCaptureList.size - 1

    for (capture in mCaptureList) {
        if (index <= 10) {
            sumLatest10XValues += capture.captureValueX
            sumLatest10YValues += capture.captureValueY
            sumLatest10ZValues += capture.captureValueZ
            index++
        } else {
            oneRowBackInList = index - 1
            tenRowsBackInList = index - 10
            oldestRowIn10XValues = mCaptureList[tenRowsBackInList].captureValueX
            oldestRowIn10YValues = mCaptureList[tenRowsBackInList].captureValueY
            oldestRowIn10ZValues = mCaptureList[tenRowsBackInList].captureValueZ
            sumLatest10XValues = capture.captureValueX - oldestRowIn10XValues
            sumLatest10YValues += capture.captureValueY - oldestRowIn10YValues
            sumLatest10ZValues += capture.captureValueZ - oldestRowIn10ZValues
            xValueSquared = sumLatest10XValues * sumLatest10YValues
            yValueSquared = sumLatest10XValues * sumLatest10YValues
            zValueSquared = sumLatest10XValues * sumLatest10YValues
            sumOfSquaredValues = xValueSquared + yValueSquared + zValueSquared
            squareRootofValues = sqrt(sumOfSquaredValues)
            currentDuration = capture.duration - mCaptureList[oneRowBackInList].duration
            currentDistanceTravelled = squareRootofValues.toDouble() * currentDuration
            totalDistanceTravelled += currentDistanceTravelled
            mAverageSpeed = totalDistanceTravelled / capture.duration

            if (totalDistanceTravelled > metreClicker) {
                mTimeToEachMetre.add(totalDistanceTravelled)
                metreClicker += 1.0
            }

            if (totalDistanceTravelled > tenMetreClicker) {
                mTimeToEachTenMetres.add(totalDistanceTravelled)
                tenMetreClicker += 10
            }
        }
    }

    mCalculatedValues =
        CalculatedValues(
            timestamp = mCaptureList[firstRowInCaptureList].timestamp.toString(),
            averageSpeed = mAverageSpeed,
            totalDistance = totalDistanceTravelled,
            totalTime = mCaptureList[lastRowInCaptureList].duration,
            timeToEachMetre = mTimeToEachMetre,
            timeToEachTenMetres = mTimeToEachTenMetres,
        )

    return mCalculatedValues
}