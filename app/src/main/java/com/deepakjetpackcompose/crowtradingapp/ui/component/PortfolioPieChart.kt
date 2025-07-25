package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.crowtradingapp.domain.model.PieChartModel

@Composable
fun PortfolioPieChart(
    coins: List<PieChartModel>,
    modifier: Modifier = Modifier,
    totalAmount: String = "$1800"
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            coins.forEach { coin ->
                val sweepAngle = 360 * (coin.percentage / 100)
                drawArc(
                    color = coin.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
                startAngle += sweepAngle
            }
        }

        // Donut hole effect
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Black,
                radius = size.minDimension / 2.8f
            )
        }

        // Center Label
        Text(
            text = totalAmount,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
