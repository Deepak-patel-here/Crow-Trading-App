package com.deepakjetpackcompose.crowtradingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Roi(
    val currency: String,
    val percentage: Double,
    val times: Double
)