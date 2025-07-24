package com.deepakjetpackcompose.crowtradingapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.model.BuyCoinModel
import com.deepakjetpackcompose.crowtradingapp.ui.component.BuyCoinDialog
import com.deepakjetpackcompose.crowtradingapp.ui.component.CandleChartView
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.CustomDropDownComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.GlassMorphismIconComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.TransactionButton
import com.deepakjetpackcompose.crowtradingapp.ui.component.toPercentage
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.CoinViewModel
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.UpdateBalance
import kotlinx.coroutines.delay


@Composable
fun TradingScreen(
    navController: NavController,
    id: String,
    symbol: String,
    currentPrice: Double,
    percentage: Double,
    image: String,
    name: String,
    price: List<Double>,
    price_change_24h: Double,
    balance: Double,
    modifier: Modifier = Modifier,
    coinViewModel: CoinViewModel = hiltViewModel<CoinViewModel>(),
    authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
) {
    val context = LocalContext.current
    val isFav = authViewModel.isFav.collectAsState()
    val coinChart = coinViewModel.coinChart.collectAsStateWithLifecycle()
    val data = coinChart.value.listOfCoins
    val truePercentage = toPercentage(value = percentage, total = 100.00)
    val color = if (percentage > 0) Color(0xFF02C173) else Color(0xFfE11A38)
    var showDialog by remember { mutableStateOf(false) }
    val loading=authViewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.isCoinFavorite(id)
        while (true) {
            coinViewModel.getCoinChart(id)
            delay(30000)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEF6C00).copy(alpha = 0.7f),
                        Color(0xFF3E2723).copy(alpha = 1f),
                        Color(0xFF161514)
                    ),
                    startY = 0f,
                    endY = 1000f
                )
            )
            .padding(vertical = 10.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            GlassMorphismIconComponent(
                size = 50.dp,
                image = R.drawable.back_trading,
                color = Color.White,
                imgSize = 24.dp,
                onClick = {
                    navController.popBackStack()
                })

            Text(
                "${symbol.toUpperCase()}/ USDT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            GlassMorphismIconComponent(
                size = 50.dp,
                image = if (!isFav.value) R.drawable.star_trading else R.drawable.star_filled_trading,
                color = if (!isFav.value) Color.White else Color.Yellow,
                imgSize = 24.dp,
                onClick = {
                    authViewModel.addCoinToFavorites(
                        id = id,
                        image = image,
                        name = name,
                        price_change_24h = price_change_24h,
                        currentPrice = currentPrice,
                        symbol = symbol,
                        percentage = percentage,
                        sparklineIn7d = price
                    ) { success, msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                })
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "$$currentPrice",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(6.dp))
                Text(truePercentage, fontSize = 16.sp, color = color)
            }

            Column(horizontalAlignment = Alignment.End) {
                CustomDropDownComponent(text = "1 hour", height = 29.dp, width = 100.dp)
                Spacer(Modifier.height(6.dp))
                CustomDropDownComponent(text = "Indicator", height = 29.dp, width = 120.dp)
            }
        }

        Spacer(Modifier.height(25.dp))
        CandleChartView(candleEntries = data, modifier = Modifier.weight(1f))
        Spacer(Modifier.height(25.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TransactionButton(
                title = "Buy",
                onClick = { showDialog = true },
                color = Color(0xFF02C173)
            )
            TransactionButton(title = "Sell", onClick = {}, color = Color(0xFfE11A38))
        }

        Spacer(Modifier.height(25.dp))


    }
    if (coinChart.value.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f)), // semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CoinLoader(size = 300.dp)
        }
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {


            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    BuyCoinDialog(
                        name = name,
                        symbol = symbol,
                        price = currentPrice,
                        image = image,
                        balance = balance,
                        onBuy = {cnt,payable->
                           val  coinModel= BuyCoinModel(
                                id=id,
                               symbol = symbol,
                               name = name,
                               image = image,
                               current_price = currentPrice,
                               price_change_percentage_24h = percentage,
                               price = price,
                               price_change_24h = price_change_24h,
                               boughtPrice = payable,
                               coinCnt = cnt

                            )
                            authViewModel.buyCoin(
                                buyModel = coinModel,
                                quantityToBuy = cnt,
                            ){
                                    success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }

                            showDialog=false
                        },
                        onCancel = { showDialog = false })
                }
            }
        }
    }

    if(loading.value== UpdateBalance.Loading){
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