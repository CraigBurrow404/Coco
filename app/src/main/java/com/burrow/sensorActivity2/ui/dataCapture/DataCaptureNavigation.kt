package com.burrow.sensorActivity2.ui.dataCapture

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.sensorApp.SensorAppEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataCaptureAppBar(
    modifier: Modifier = Modifier,
    currentScreen: SensorAppEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
) {
    println("DataCaptureAppBar Called")
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button) //###this is not on screen
                    )
                }
            }
        }
    )
}