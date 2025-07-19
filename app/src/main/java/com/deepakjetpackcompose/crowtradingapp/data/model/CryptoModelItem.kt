package com.deepakjetpackcompose.crowtradingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoModelItem(
    val ath: Double? = null,
    val ath_change_percentage: Double? = null,
    val ath_date: String? = null,
    val atl: Double? = null,
    val atl_change_percentage: Double? = null,
    val atl_date: String? = null,
    val circulating_supply: Double? = null,
    val current_price: Double? = null,
    val fully_diluted_valuation: Long? = null,
    val high_24h: Double? = null,
    val id: String? = null,
    val image: String? = null,
    val last_updated: String? = null,
    val low_24h: Double? = null,
    val market_cap: Long? = null,
    val market_cap_change_24h: Double? = null,
    val market_cap_change_percentage_24h: Double? = null,
    val market_cap_rank: Int? = null,
    val max_supply: Double? = null,
    val name: String? = null,
    val price_change_24h: Double? = null,
    val price_change_percentage_24h: Double? = null,
    val roi: Roi? = null,
    val sparkline_in_7d: SparklineIn7d? = null,
    val symbol: String? = null,
    val total_supply: Double? = null,
    val total_volume: Long? = null
)
