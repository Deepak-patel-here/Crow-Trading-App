package com.deepakjetpackcompose.crowtradingapp.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.data.remote.CoinRepository
import com.deepakjetpackcompose.crowtradingapp.domain.model.Candles
import com.deepakjetpackcompose.crowtradingapp.domain.model.convertToCandleEntries
import com.deepakjetpackcompose.crowtradingapp.domain.model.mapToCandleEntries
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(private val repo: CoinRepository): ViewModel() {

    private val _coinList= MutableStateFlow<CoinList>(CoinList())
    val coinList=_coinList.asStateFlow()

    private val _coinChart = MutableStateFlow<CoinChart>(CoinChart())
    val coinChart = _coinChart.asStateFlow()



    fun getAllCoins( perPage: Int = 20){
        _coinList.value=CoinList(loading = true)
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response=repo.getAllCoins(perpage = perPage)
                _coinList.value= CoinList(loading = false, listOfCoins = response)
                Log.d("api",response[0].toString())
            }catch(e: Exception){
                _coinList.value=CoinList(loading = false,error = e.message.toString())
            }
        }
    }

    fun getCoinChart(coinId:String){
        _coinChart.value=CoinChart(loading = true,listOfCoins = emptyList())
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response: List<List<Double>>? =repo.getRealTimeChart(coinId)
                Log.d("api",response?.get(0).toString())
                if(response!=null) {
                    val candles: List<Candles> = convertToCandleEntries(response)
                    val candleEntry = mapToCandleEntries(data = candles)
                    _coinChart.value=CoinChart(loading = false,listOfCoins = candleEntry)
                }else
                    _coinChart.value=CoinChart(loading = false,error = "No Data Found")
            }catch (e: Exception){
                _coinChart.value=CoinChart(loading = false,error = e.message.toString())
            }
        }
    }

    fun storeImageToCloud(context: Context,
                          imageUri: Uri,
                          onUploaded: (String) -> Unit){
        viewModelScope.launch {
            repo.uploadImageToCloudinary(context = context,imageUri=imageUri,onUploaded = onUploaded)
        }
    }




}
data class CoinList(
    val loading: Boolean=false,
    val listOfCoins: List<CryptoModelItem> =emptyList(),
    val error: String=""
)

data class CoinChart(
    val loading: Boolean=false,
    val listOfCoins: List<CandleEntry> =emptyList(),
    val error: String=""
)