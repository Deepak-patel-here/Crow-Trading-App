package com.deepakjetpackcompose.crowtradingapp.ui.component

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.graphics.toColorInt

@Composable
fun CandleChartView(
    candleEntries: List<CandleEntry>,
    modifier: Modifier = Modifier
) {
    if (candleEntries.isEmpty()) {
        Text(text = "No candle data available.")
        return
    }

    AndroidView(factory = { context ->
        CandleStickChart(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            description.isEnabled = false
            legend.isEnabled = false

            // X-Axis: Remove line + labels styling
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                setDrawLabels(true)
                setDrawGridLines(true)
                textColor = Color.WHITE
                granularity = 1f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(value.toLong()))
                    }
                }
            }

            axisLeft.isEnabled = false

            axisRight.apply {
                setDrawAxisLine(false)
                setDrawLabels(true)
                setDrawGridLines(true)
                textColor = Color.WHITE
            }

            // Chart dataset
            val dataSet = CandleDataSet(candleEntries, "BTC").apply {
                shadowColor = Color.DKGRAY
                shadowWidth = 0.7f
                decreasingColor = "#E11A38".toColorInt()
                decreasingPaintStyle = Paint.Style.FILL
                increasingColor = "#02C173".toColorInt()
                increasingPaintStyle = Paint.Style.FILL
                neutralColor = Color.GRAY
                setDrawValues(false)
            }

            data = CandleData(dataSet)
            invalidate()
        }
    }, modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
    )
}



