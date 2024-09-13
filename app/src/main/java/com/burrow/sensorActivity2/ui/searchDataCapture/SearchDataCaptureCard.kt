package com.burrow.sensorActivity2.ui.searchDataCapture

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burrow.sensorActivity2.ui.common.getSensorTypeName
import com.burrow.sensorActivity2.ui.common.setSecondaryButtonColor
import com.burrow.sensorActivity2.ui.common.setTertiaryButtonColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun SearchDataCaptureCard(
    viewModel: SearchDataCaptureViewModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {

    val secondaryButtonColor = setSecondaryButtonColor()
    val tertiaryButtonColor = setTertiaryButtonColor()

    val uniqueID : Long = viewModel.uID
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss a ")
    val uniqueIDFormatted = sdf.format(uniqueID)

    val sensorName : String = viewModel.sensorName
    val sensorNameFormatted : String = getSensorTypeName(sensorName)

    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
    )
    {
        Spacer(modifier = Modifier.weight(0.1f)) // Move Button to Horizontal centre
        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.8f)
                .weight(0.8f),
            onClick = onClick,
            shape = RoundedCornerShape(4.dp),
            colors = tertiaryButtonColor,
        ) {
            Text(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.CenterVertically),
                text = "$uniqueIDFormatted $sensorNameFormatted",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
    Spacer(modifier = Modifier.height(32.dp))
}