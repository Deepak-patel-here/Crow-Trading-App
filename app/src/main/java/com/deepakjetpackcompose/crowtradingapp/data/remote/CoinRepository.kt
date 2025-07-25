package com.deepakjetpackcompose.crowtradingapp.data.remote

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BASE_URL
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BASE_URL2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
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

    fun uploadImageToCloudinary(
        context: Context,
        imageUri: Uri,
        onUploaded: (String) -> Unit
    ) {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes() ?: return

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "image.jpg",
                bytes.toRequestBody("image/*".toMediaTypeOrNull()))
            .addFormDataPart("upload_preset", "profile_pic")
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/dnyyre0cy/image/upload")
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val json = JSONObject(body ?: "")
                val url = json.getString("secure_url")

                Handler(Looper.getMainLooper()).post {
                    onUploaded(url)
                }
            }
        })
    }

}
