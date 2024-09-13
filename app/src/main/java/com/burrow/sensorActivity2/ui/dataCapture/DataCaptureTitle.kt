package com.burrow.sensorActivity2.ui.dataCapture

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DataCaptureTitle(
    viewModel: DataCaptureViewModel,
    tertiaryButtonColor: ButtonColors,
    uiState: DataCaptureUiState,
    modifier: Modifier
) {
    Button(
        modifier = modifier
            .height(64.dp),
        onClick = { },
        shape = RoundedCornerShape(4.dp),
        colors = tertiaryButtonColor,
    ) {
        Text(
            text = uiState.sensorName,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}