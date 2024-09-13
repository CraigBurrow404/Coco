package com.burrow.sensorActivity2.ui.dataCapture

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun CaptureCountValue( mCaptureCount: Int, modifier: Modifier ) {
    Box( modifier = modifier, contentAlignment = Alignment.CenterEnd ) {
        Text(
            modifier = modifier,
            fontSize = 32.sp,
            text = "$mCaptureCount",
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    ),
                    textAlign = TextAlign.End
                )
            )
        )
    }
}