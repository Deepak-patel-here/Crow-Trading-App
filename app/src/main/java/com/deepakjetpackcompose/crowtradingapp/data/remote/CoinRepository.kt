package com.deepakjetpackcompose.crowtradingapp.data.remote

import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BASE_URL
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BASE_URL2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CoinRepository @Inject constructor(private val apiClient: HttpClient)  {

    suspend fun getAllCoins( perpage: Int = 20,): List<CryptoModelItem> {
        try {
            val response = apiClient.get(BASE_URL) {
                parameter("vs_currency", "usd")
                parameter("order", "market_cap_desc")
                parameter("per_page", perpage)
                parameter("page", 1)
                parameter("sparkline", true)
            }.body<List<CryptoModelItem>>()

            return response
        }catch ( e: Exception){
            return emptyList<CryptoModelItem>()
        }
    }

    suspend fun getRealTimeChart(coinId: String = "bitcoin",
                                 vsCurrency: String = "usd",
                                 days: Int = 1): List<List<Double>>?{
        try {
            val response = apiClient.get("https://api.coingecko.com/api/v3/coins/$coinId/ohlc") {
                parameter("vs_currency", vsCurrency)
                parameter("days", days) // 1 = last 24 hours
            }.body< List<List<Double>>>()
            return response
        }catch (e: Exception){
            return emptyList()
        }
    }
}
