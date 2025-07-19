package com.deepakjetpackcompose.crowtradingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.data.remote.CoinRepository
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

    fun getAllCoins(){
        _coinList.value=CoinList(loading = true)
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response=repo.getAllCoins()
                _coinList.value= CoinList(loading = false, listOfCoins = response)
                Log.d("api",response[0].toString())
            }catch(e: Exception){
                _coinList.value=CoinList(loading = false,error = e.message.toString())
            }
        }
    }


}
data class CoinList(
    val loading: Boolean=false,
    val listOfCoins: List<CryptoModelItem> =emptyList(),
    val error: String=""
)