package com.burrow.sensorActivity2.ui.charts

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.math.roundToInt

// date + balance
// list of date + balanc
@RequiresApi(Build.VERSION_CODES.O)
val graphData2 = listOf(
    Balance2(LocalDate.now(), BigDecimal(65631)),
    Balance2(LocalDate.now().plusWeeks(1), BigDecimal(65931)),
    Balance2(LocalDate.now().plusWeeks(2), BigDecimal(65851)),
    Balance2(LocalDate.now().plusWeeks(3), BigDecimal(65931)),
    Balance2(LocalDate.now().plusWeeks(4), BigDecimal(66484)),
    Balance2(LocalDate.now().plusWeeks(5), BigDecimal(67684)),
    Balance2(LocalDate.now().plusWeeks(6), BigDecimal(66684)),
    Balance2(LocalDate.now().plusWeeks(7), BigDecimal(66984)),
    Balance2(LocalDate.now().plusWeeks(8), BigDecimal(70600)),
    Balance2(LocalDate.now().plusWeeks(9), BigDecimal(71600)),
    Balance2(LocalDate.now().plusWeeks(10), BigDecimal(72600)),
    Balance2(LocalDate.now().plusWeeks(11), BigDecimal(72526)),
    Balance2(LocalDate.now().plusWeeks(12), BigDecimal(72976)),
    Balance2(LocalDate.now().plusWeeks(13), BigDecimal(73589)),
)

val BarColor2 = Color.White.copy(alpha = 0.3f)
val HighlightColor2 = Color.White.copy(alpha = 0.7f)

@OptIn(ExperimentalTextApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun XAxisGraph(modifier : Modifier) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        val animationProgress = remember { Animatable(0f) }
        val highlightedWeek by remember { mutableStateOf<Int?>(null) }
        val localView = LocalView.current

        LaunchedEffect(highlightedWeek) {
            if (highlightedWeek != null) {
                localView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
        }

        LaunchedEffect(key1 = graphData2, block = {
            animationProgress.animateTo(1f, tween(3000))
        })

        val coroutineScope = rememberCoroutineScope()
        val textMeasurer = rememberTextMeasurer()
        val labelTextStyle = MaterialTheme.typography.labelSmall

        Spacer(
            modifier = Modifier
                // .padding(8.dp)
                .aspectRatio(3 / 2f)
                .fillMaxSize()
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTapGestures {
                        coroutineScope.launch {
                            animationProgress.snapTo(0f)
                            animationProgress.animateTo(1f, tween(3000))
                        }
                    }
                }
                .drawWithCache {
                    val path = generatePath2(graphData2, size)
                    val filledPath = Path()
                    filledPath.addPath(path)
                    filledPath.relativeLineTo(0f, size.height)
                    filledPath.lineTo(0f, size.height)
                    filledPath.close()

                    onDrawBehind {
                        val barWidthPx = 1.dp.toPx()
                        drawRect(BarColor, style = Stroke(barWidthPx))

                        val verticalLines = 4
                        val verticalSize = size.width / (verticalLines + 1)
                        repeat(verticalLines) { i ->
                            val startX = verticalSize * (i + 1)
                            drawLine(
                                BarColor2,
                                start = Offset(startX, 0f),
                                end = Offset(startX, size.height),
                                strokeWidth = barWidthPx
                            )
                        }
                        val horizontalLines = 3
                        val sectionSize = size.height / (horizontalLines + 1)
                        repeat(horizontalLines) { i ->
                            val startY = sectionSize * (i + 1)
                            drawLine(
                                BarColor2,
                                start = Offset(0f, startY),
                                end = Offset(size.width, startY),
                                strokeWidth = barWidthPx
                            )
                        }

                        // draw line
                        clipRect(right = size.width * animationProgress.value) {
                            drawPath(path, Color.Blue, style = Stroke(2.dp.toPx()))

                            drawPath(
                                filledPath,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Blue.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                style = Fill
                            )
                        }

                        // draw highlight if user is dragging
                        highlightedWeek?.let {
                            this.drawHighlight2(it, graphData2, textMeasurer, labelTextStyle)
                        }

                    }
                }
        )
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawHighlight2(
    highlightedWeek: Int,
    graphData: List<Balance2>,
    textMeasurer: TextMeasurer,
    labelTextStyle: TextStyle
) {
    val amount = graphData[highlightedWeek].amount
    val minAmount = graphData.minBy { it.amount }.amount
    val range = graphData.maxBy { it.amount }.amount - minAmount
    val percentageHeight = ((amount - minAmount).toFloat() / range.toFloat())
    val pointY = size.height - (size.height * percentageHeight)

    // draw vertical line on week
    val x = highlightedWeek * (size.width / (graphData.size - 1))
    drawLine(
        HighlightColor2,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = 2.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    // draw hit circle on graph
    drawCircle(
        Color.Green,
        radius = 4.dp.toPx(),
        center = Offset(x, pointY)
    )

    // draw info box
    val textLayoutResult = textMeasurer.measure("$amount", style = labelTextStyle)
    val highlightContainerSize = (textLayoutResult.size).toIntRect().inflate(4.dp.roundToPx()).size
    val boxTopLeft = ( x - (highlightContainerSize.width / 2f))
        .coerceIn(0f, size.width - highlightContainerSize.width)
    drawRoundRect(
        Color.White,
        topLeft = Offset(boxTopLeft, 0f),
        size = highlightContainerSize.toSize(),
        cornerRadius = CornerRadius(8.dp.toPx())
    )
    drawText(
        textLayoutResult,
        color = Color.Black,
        topLeft = Offset(boxTopLeft + 4.dp.toPx(), 4.dp.toPx())
    )
}

