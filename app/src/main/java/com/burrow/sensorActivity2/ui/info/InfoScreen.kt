package com.burrow.sensorActivity2.ui.info

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
import com.burrow.sensorActivity2.R
import com.burrow.sensorActivity2.ui.theme.onPrimaryLight

@Composable
fun InfoScreen(
) {

// TODO the UIs sole responsibility should be to consume and display UI state.

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Spacer(modifier = Modifier.height(128.dp))
        Text(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(.9f),
            text = stringResource(R.string.info_text),
            color = onPrimaryLight,
        )
    }
}