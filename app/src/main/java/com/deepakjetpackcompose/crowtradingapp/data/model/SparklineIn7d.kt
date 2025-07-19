package com.deepakjetpackcompose.crowtradingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SparklineIn7d(
    val price: List<Double>
)