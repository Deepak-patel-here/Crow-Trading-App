package com.deepakjetpackcompose.crowtradingapp.ui.component

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.deepakjetpackcompose.crowtradingapp.domain.model.Candles
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry

@Composable
fun CandleChartView(candleEntries: List<CandleEntry>) {
    AndroidView(factory = { context ->
        val candleChart = CandleStickChart(context)

        val dataSet = CandleDataSet(candleEntries, "Bitcoin").apply {
            color = Color.rgb(80, 80, 80)
            shadowColor = Color.DKGRAY
            decreasingColor = Color.RED
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = Color.GREEN
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.BLUE
        }

        candleChart.data = CandleData(dataSet)
        candleChart.invalidate()
        candleChart
    })
}