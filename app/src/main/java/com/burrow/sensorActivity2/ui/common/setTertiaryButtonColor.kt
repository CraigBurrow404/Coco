package com.burrow.sensorActivity2.ui.common

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun setTertiaryButtonColor(): ButtonColors {
    val tertiaryButtonColor = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.tertiaryContainer
    )
    return tertiaryButtonColor
}