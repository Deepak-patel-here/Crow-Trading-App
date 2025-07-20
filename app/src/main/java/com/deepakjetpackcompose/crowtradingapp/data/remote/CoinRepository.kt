package com.deepakjetpackcompose.crowtradingapp.data.remote

import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CoinRepository @Inject constructor(private val apiClient: HttpClient)  {

    suspend fun getAllCoins(): List<CryptoModelItem> {
        try {
            val response = apiClient.get(BASE_URL) {
                parameter("vs_currency", "usd")
                parameter("order", "market_cap_desc")
                parameter("per_page", 20)
                parameter("page", 1)
                parameter("sparkline", true)
            }.body<List<CryptoModelItem>>()

            return response
        }catch ( e: Exception){
            return emptyList<CryptoModelItem>()
        }
    }

    suspend fun getRealTimeChart(coinId:String): List<List<Double>>?{
        try {
            val response = apiClient.get("$BASE_URL/$coinId/ohcl") {
                parameter("vs_currency", "usd")
                parameter("days", 1) // 1 = last 24 hours
            }.body< List<List<Double>>>()
            return response
        }catch (e: Exception){
            return emptyList()
        }
    }
}
