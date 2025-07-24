package com.deepakjetpackcompose.crowtradingapp.domain.model

data class Transaction(
    val transactionId: String?="",
    val symbol:String?="",
    val name:String?="",
    val id:String?="",
    val totalAmount:Double?=0.00,
    val coinCount:Int?=0,
    val date:String?=""
)
