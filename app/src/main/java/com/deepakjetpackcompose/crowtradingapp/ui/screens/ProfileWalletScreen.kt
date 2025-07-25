package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.model.BuyCoinModel
import com.deepakjetpackcompose.crowtradingapp.domain.model.PieChartModel
import com.deepakjetpackcompose.crowtradingapp.domain.model.Transaction
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.LogoutButton
import com.deepakjetpackcompose.crowtradingapp.ui.component.PortfolioPieChart
import com.deepakjetpackcompose.crowtradingapp.ui.component.toPercentage
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel
import kotlin.random.Random

@Composable
fun ProfileWalletScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewmodel: AuthViewModel = hiltViewModel<AuthViewModel>()
) {

    val transaction = authViewmodel.transactions.collectAsState()
    val user = authViewmodel.user.collectAsState()
    val boughtCoins = authViewmodel.boughtCoins.collectAsState()
    val loader = authViewmodel.profileLoading.collectAsState()


    LaunchedEffect(Unit) {
        authViewmodel.getUser()
        authViewmodel.fetchBoughtCoins()
        authViewmodel.fetchTransactions()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF161616))
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp)
    ) {
        ProfileCard(name = user.value.name, email = user.value.email)
        Spacer(Modifier.height(16.dp))
        WalletBalanceSection(balance = user.value.balance?.toDouble() ?: 0.00)
        Spacer(Modifier.height(16.dp))
        CoinHoldingsCarousel(boughtList = boughtCoins.value)
        Spacer(Modifier.height(16.dp))
        TransactionHistory(transaction.value)
        Spacer(Modifier.height(16.dp))
        PortfolioChart(boughtList = boughtCoins.value)
        Spacer(Modifier.height(16.dp))
        SecuritySettingsCard()
        Spacer(Modifier.height(16.dp))
        QuickActionsRow()
        Spacer(Modifier.height(16.dp))
        ReferralProgramCard()
        Spacer(Modifier.height(16.dp))
        DarkModeToggle()
        Spacer(Modifier.height(8.dp))
        LogoutButton(onClick = {
            authViewmodel.signOut()
            navController.navigate(NavigationHelper.LoginScreen) {
                popUpTo(NavigationHelper.ProfileWalletScreen) { inclusive = true }
            }
        })
    }
    if (loader.value) {
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

@Composable
fun ProfileCard(name: String, email: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://placehold.co/100x100.png",
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    name,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(email, fontSize = 14.sp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun WalletBalanceSection(balance: Double) {
    val rupees = balance * 86.9
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(listOf(Color(0xFF00C6FF), Color(0xFF0072FF)))
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Total Balance", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                Text(
                    "$$balance USDT",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text("₹$rupees", fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }
    }
}

@Composable
fun CoinHoldingsCarousel(boughtList: List<BuyCoinModel>) {
    if (boughtList.isEmpty()) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(130.dp),
            colors = CardDefaults.cardColors(Color(0xFF1F1F1F)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No coins bought yet", color = Color.White)
            }
        }
    } else {

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(boughtList) { coin ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(130.dp),
                    colors = CardDefaults.cardColors(Color(0xFF1F1F1F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = coin.image,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text=coin.symbol.toString().uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("${coin.coinCnt}", color = Color.White)
                        Text(
                            "${coin.current_price}",
                            color = Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            toPercentage(
                                value = coin.price_change_percentage_24h ?: 0.00,
                                total = 100.0
                            ), color = Color.Green, fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionHistory(list: List<Transaction>) {
    Column {
        Text(
            "Transaction History",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))

        if (list.isEmpty()) {
            Text("No transactions yet", color = Color.LightGray)
        } else {
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(list) { coin ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.transaction),
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("${coin.mode} ${coin.symbol}", color = Color.White)
                                Text(
                                    "${coin.coinCount}  ${coin.symbol} at $${coin.totalAmount}",
                                    fontSize = 12.sp,
                                    color = Color.LightGray
                                )
                            }
                        }
                        Text("${coin.date}", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun PortfolioChart(boughtList: List<BuyCoinModel>) {
    Text(
        "Portfolio Allocation",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(8.dp))
    // Add charting logic with a charting library like MPAndroidChart or Accompanist Canvas here
    var totalAmount=0.00
    val list = boughtList.map {

        totalAmount+=(it.coinCnt?:0)*(it.current_price?:0.00)

        PieChartModel(
            it.symbol.toString(),
            it.price_change_percentage_24h?.toFloat() ?: 0.0f,
            color = getRandomColor()
        )

    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.DarkGray.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        PortfolioPieChart(data = list)
    }
}


@Composable
fun SecuritySettingsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFF1C1C1C))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Security Settings",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            SettingRow("2FA", true)
            SettingRow("Email Verified", true)
            SettingRow("Change Password", false)
        }
    }
}

@Composable
fun SettingRow(label: String, status: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White)
        Text(if (status) "Enabled" else "Disabled", color = if (status) Color.Green else Color.Red)
    }
}

@Composable
fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.download_tradeing_2),
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.upload),
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.swap),
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.history),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun ReferralProgramCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFF252525))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Referral Program", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text("Earn rewards for inviting friends!", color = Color.LightGray, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Button(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                Text("Share Now")
            }
        }
    }
}

@Composable
fun DarkModeToggle() {
    var isDark by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Dark Mode", color = Color.White)
        Switch(checked = isDark, onCheckedChange = { isDark = it })
    }
}

fun getRandomColor(): Color {
    val random = Random(System.nanoTime())
    val red = random.nextInt(100, 256) // avoid very dark colors
    val green = random.nextInt(100, 256)
    val blue = random.nextInt(100, 256)
    return Color(red, green, blue)
}


