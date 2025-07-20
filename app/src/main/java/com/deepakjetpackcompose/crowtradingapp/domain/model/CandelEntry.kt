package com.deepakjetpackcompose.crowtradingapp.domain.model

import com.github.mikephil.charting.data.CandleEntry

data class Candles(
    val timeStamp: Long,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float)



fun convertToCandleEntries(data: List<List<Double>>): List<Candles>{
    return data.map {values->
        Candles(
            timeStamp = values[0].toLong(),
            open = values[1].toFloat(),
            high = values[2].toFloat(),
            low = values[3].toFloat(),
            close = values[4].toFloat()
        )
    }
}

fun mapToCandleEntries(data: List<Candles>): List<CandleEntry> {
    return data.mapIndexed { index, candle ->
        CandleEntry(
            index.toFloat(),
            candle.high,
            candle.low,
            candle.open,
            candle.close
        )
    }
}

