package com.burrow.sensorActivity2.ui.common

import android.hardware.Sensor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.burrow.sensorActivity2.ui.Dimens.ExtraSmallPadding2
import com.burrow.sensorActivity2.ui.Dimens.MediumPadding1
import com.burrow.sensorActivity2.ui.SelectedSensors.ChooseSensorCard


@Composable
fun SensorsList(
    modifier: Modifier = Modifier,
    sensors: List<Sensor>,
    onClick: (Sensor) -> Unit
) {
    if (sensors.isEmpty()){
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = sensors.size,
        ) {
            sensors[it].let { sensor ->
                ChooseSensorCard(sensor = sensor, onClick = { onClick(sensor) })
            }
        }
    }

}

@Composable
fun SensorsList(
    modifier: Modifier = Modifier,
    sensors: LazyPagingItems<Sensor>,
    onClick: (Sensor) -> Unit
) {

    val handlePagingResult = handlePagingResult(sensors)


    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(
                count = sensors.itemCount,
            ) {
                sensors[it]?.let { sensor ->
                    ChooseSensorCard(sensor = sensor, onClick = { onClick(sensor) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(sensors: LazyPagingItems<Sensor>): Boolean {
    val loadState = sensors.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}