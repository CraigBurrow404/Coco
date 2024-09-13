package com.burrow.sensorActivity2.ui.SelectedSensors

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.burrow.sensorActivity2.R

@Composable
fun SelectDataScreen(
    viewModel: SelectDataViewModel,
    navController: NavController
) {

// TODO the UI's sole responsibility should be to consume and display UI state.

    val TAG = "MyActivity"
    Log.v(TAG, "SelectDateScreen Started")

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(stringResource(R.string.cowabunga))
        }
    }
}

