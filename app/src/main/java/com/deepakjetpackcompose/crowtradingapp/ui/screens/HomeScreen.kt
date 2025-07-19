package com.deepakjetpackcompose.crowtradingapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.ui.component.TransactionButton
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.CoinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.CryptoShowComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.FavoriteCoinComponent
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    coinViewModel: CoinViewModel = hiltViewModel<CoinViewModel>()
) {

    val coins = coinViewModel.coinList.collectAsState()
    val coinList = coins.value.listOfCoins

    LaunchedEffect(Unit) {
        Log.d("COIN_DEBUG", "Updated coin list: ${coinList.size}")
        coinViewModel.getAllCoins()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFEF6C00).copy(alpha = 0.2f),
                        Color(0xFF3E2723).copy(alpha = 0.2f),
                        Color(0xFF161514)
                    ),
                    center = Offset(500f, 0f),
                    radius = 600f
                )
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(Modifier.height(70.dp))
        Text("Total balance", color = Color.LightGray, fontSize = 16.sp)
        Spacer(Modifier.height(6.dp))
        Text(
            "$5,450.500",
            color = Color.White,
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TransactionButton(img = R.drawable.download_trading, title = "Deposit", onClick = {})
            TransactionButton(img = R.drawable.upload, title = "Withdraw", onClick = {})
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
            item {
                Text(
                    "Favorites",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(20.dp))

            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(2) {
                        FavoriteCoinComponent()
                    }
                }
                Spacer(Modifier.height(20.dp))


            }

            item {
                Text(
                    "Popular cryptocurrencies",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Spacer(Modifier.height(10.dp))

            }

            items(coinList) { coin ->
                CryptoShowComponent(coinModel = coin, onClick = {})
            }
        }

    }
    if (coins.value.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f)), // semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CoinLoader(size = 300.dp)
        }
    }

}

