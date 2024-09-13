package com.burrow.sensorActivity2.ui.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Preview
@Composable
fun CoordinateSystem1(){
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val barWidthPx = 1.dp.toPx()

            drawRect(Color.White)
            val verticalLines = size.width / 80.dp.toPx()
            val verticalSize = size.width / (verticalLines + 1)
            repeat(verticalLines.roundToInt()) { i ->
                val startX = verticalSize * (i + 1)
                drawLine(
                    Color.Gray,
                    start = Offset(startX, 0f),
                    end = Offset(startX, size.height),
                    strokeWidth = barWidthPx
                )
            }

            val horizontalLines = size.height / 80.dp.toPx()
            val sectionSize = size.height / (horizontalLines + 1)
            repeat(horizontalLines.roundToInt()) { i ->
                val startY = sectionSize * (i + 1)
                drawLine(
                    BarColor,
                    start = Offset(0f, startY),
                    end = Offset(size.width, startY),
                    strokeWidth = barWidthPx
                )
            }

        })
}