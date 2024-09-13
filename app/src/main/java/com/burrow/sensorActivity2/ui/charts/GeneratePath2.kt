package com.burrow.sensorActivity2.ui.charts

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun generatePath2(data: List<Balance2>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount } // will map to x= 0, y = height
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() *
                        heightPxPerAmount
            )
        }
        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() *
                heightPxPerAmount
        path.lineTo(balanceX, balanceY)
    }
    return path
}