 package com.burrow.sensorActivity2.ui.sensorApp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorAppBar(
    modifier: Modifier = Modifier,
    currentScreen: SensorAppEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    navController : NavController
) {
    CenterAlignedTopAppBar(title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier,
        navigationIcon = @androidx.compose.runtime.Composable {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = stringResource(R.string.exit)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(route = SensorAppEnum.InfoScreen.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Menu Button"
                )
            }
        },
    )
}