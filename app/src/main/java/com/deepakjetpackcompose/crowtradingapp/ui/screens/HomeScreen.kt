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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.deepakjetpackcompose.crowtradingapp.data.model.SparklineIn7d
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.CryptoShowComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.FavoriteCoinComponent
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthState
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    coinViewModel: CoinViewModel = hiltViewModel<CoinViewModel>(),
    authViewModel: AuthViewModel=hiltViewModel<AuthViewModel>()
) {

    val coins = coinViewModel.coinList.collectAsState()
    val coinList = coins.value.listOfCoins
    val isRefreshing = coins.value.loading
    val favCoins=authViewModel.favCoinList.collectAsState()
    val user=authViewModel.user.collectAsState()




    LaunchedEffect(navController.currentBackStackEntry) {
        Log.d("COIN_DEBUG", "Updated coin list: ${coinList.size}")
        Log.d("COIN_DEBUG","${favCoins.value.size}")
        authViewModel.getUser()
        authViewModel.fetchFavoriteCoins()
        if(coinList.isEmpty()) {
            coinViewModel.getAllCoins()
        }
    }
    SwipeRefresh(
        state = remember { SwipeRefreshState(isRefreshing) },
        onRefresh = {
            coinViewModel.getAllCoins()
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF161514)
                )
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Spacer(Modifier.height(70.dp))
            Text("Total balance", color = Color.LightGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                "$${user.value.balance}",
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

                TransactionButton(
                    img = R.drawable.download_trading,
                    title = "Deposit",
                    onClick = {
                        navController.navigate(NavigationHelper.PaymentScreen)
                    })
                TransactionButton(img = R.drawable.upload, title = "Withdraw", onClick = {
                    navController.navigate(NavigationHelper.WithdrawScreen(balance = user.value.balance.toString()))
                })
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 80.dp)
            ) {
                item {
                    if(favCoins.value.isNotEmpty()) {
                        Text(
                            "Favorites",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Spacer(Modifier.height(20.dp))
                    }

                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(favCoins.value){coin->
                            FavoriteCoinComponent(coin = coin, onClick = {
                                navController.navigate(
                                    NavigationHelper.TradingScreen(
                                        id = coin.id,
                                        symbol = coin.symbol,
                                        current_price = coin.current_price,
                                        price_change_percentage_24h = coin.price_change_percentage_24h,
                                        image = coin.image,
                                        name = coin.name,
                                        price = coin.price,
                                        price_change_24h = coin.price_change_24h,
                                        balance = user.value.balance?.toDouble()


                                    )
                                )
                            })
                            Spacer(Modifier.width(15.dp))

                        }
                    }
                    Spacer(Modifier.height(20.dp))


                }

                item {
                    if(coinList.isNotEmpty()) {
                        Text(
                            "Popular cryptocurrencies",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Spacer(Modifier.height(10.dp))
                    }else{
                        Box(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(R.drawable.coin),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(80.dp)
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = "No coins found, please try again",
                                    color = Color.White
                                )
                            }
                        }

                    }

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

