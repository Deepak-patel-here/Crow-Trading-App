package com.deepakjetpackcompose.crowtradingapp.domain.model

data class User(
    val name:String="",
    val email:String="",
    val balance:String?="",
    val portfolio: List<Portfolio>?=emptyList(),
    val totalPnl:String?="",
    val transaction: List<Transaction>?=emptyList(),
)
