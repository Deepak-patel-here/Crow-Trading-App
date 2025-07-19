package com.deepakjetpackcompose.crowtradingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Roi(
    val currency: String? = null,
    val percentage: Double? = null,
    val times: Double? = null
)
