package com.deepakjetpackcompose.crowtradingapp.ui.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.deepakjetpackcompose.crowtradingapp.domain.model.PieChartModel
import android.graphics.Color as AndroidColor
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PortfolioPieChart(data: List<PieChartModel>) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                500
            )
            description.isEnabled = false
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelColor(AndroidColor.WHITE)
            setHoleColor(AndroidColor.TRANSPARENT)
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            setDrawEntryLabels(true)
        }
    }, update = { chart ->
        val entries = data.map {
            PieEntry(it.percentage, it.name)
        }

        val colors = data.map {
            it.color.toArgb()
        }

        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            sliceSpace = 3f
            selectionShift = 5f
        }

        chart.data = PieData(dataSet).apply {
            setDrawValues(true)
            setValueTextSize(14f)
            setValueTextColor(AndroidColor.WHITE)
        }

        chart.invalidate() // refresh chart
    })
}

