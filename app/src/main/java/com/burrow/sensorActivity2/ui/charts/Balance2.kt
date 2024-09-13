package com.burrow.sensorActivity2.ui.charts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import java.math.BigDecimal
import java.time.LocalDate

data class Balance2(val date: LocalDate, val amount: BigDecimal)