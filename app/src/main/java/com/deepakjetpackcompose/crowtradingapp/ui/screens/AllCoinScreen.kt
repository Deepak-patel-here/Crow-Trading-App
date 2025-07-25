package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.CryptoShowComponent
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.CoinViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun AllCoinScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    coinViewModel: CoinViewModel = hiltViewModel<CoinViewModel>(),
    authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
) {
    val coins = coinViewModel.coinList.collectAsState()
    val coinList = coins.value.listOfCoins
    val user = authViewModel.user.collectAsState()
    val isRefreshing = coins.value.loading



    LaunchedEffect(Unit) {
        coinViewModel.getAllCoins(perPage = 100)
        authViewModel.getUser()
    }

    SwipeRefresh(
        state = remember { SwipeRefreshState(isRefreshing) },
        onRefresh = {
            coinViewModel.getAllCoins(perPage = 100)
        }
    ) {

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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp)
            ) {

                item {
                    Text(
                        "All cryptocurrencies",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Spacer(Modifier.height(10.dp))

                }

                items(coinList) { coin ->
                    CryptoShowComponent(coinModel = coin, onClick = {
                        navController.navigate(
                            NavigationHelper.TradingScreen(
                                id = coin.id,
                                symbol = coin.symbol,
                                current_price = coin.current_price,
                                price_change_percentage_24h = coin.price_change_percentage_24h,
                                image = coin.image,
                                name = coin.name,
                                price = coin.sparkline_in_7d!!.price,
                                price_change_24h = coin.price_change_24h,
                                balance = user.value.balance?.toDouble()
                            )
                        )
                    })
                }

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