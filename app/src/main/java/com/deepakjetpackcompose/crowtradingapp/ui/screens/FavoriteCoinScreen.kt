package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.deepakjetpackcompose.crowtradingapp.ui.component.FavoriteCoinComponent
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel

@Composable
fun FavoriteCoinScreen(navController: NavController, modifier: Modifier = Modifier,authViewModel: AuthViewModel= hiltViewModel<AuthViewModel>()) {
    val favCoin=authViewModel.favCoinList.collectAsState()
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
        Text(
            "Favorite Coins",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(Modifier.height(20.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(favCoin.value){coin->
                FavoriteCoinComponent(coin=coin, onClick = {
                    navController.navigate(
                        NavigationHelper.TradingScreen(
                            id = coin.id,
                            symbol = coin.symbol,
                            current_price = coin.current_price,
                            price_change_percentage_24h = coin.price_change_percentage_24h,
                            image = coin.image,
                            name = coin.name,
                            price = coin.price,
                            price_change_24h = coin.price_change_24h


                        )
                    )
                })

            }
        }


    }

}