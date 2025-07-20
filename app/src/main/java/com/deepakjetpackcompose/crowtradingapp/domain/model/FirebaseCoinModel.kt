package com.deepakjetpackcompose.crowtradingapp.domain.model

import com.deepakjetpackcompose.crowtradingapp.data.model.SparklineIn7d

data class FirebaseCoinModel(
    val symbol:String?=null,
    val id:String?=null,
    val current_price:Double?=null,
    val price_change_percentage_24h:Double?=null,
    val image:String?=null,
    val name:String?=null,
    val price: List<Double>?=null,
    val price_change_24h: Double? = null,


)
