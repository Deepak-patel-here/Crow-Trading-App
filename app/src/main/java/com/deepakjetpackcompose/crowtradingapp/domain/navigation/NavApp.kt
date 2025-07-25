package com.deepakjetpackcompose.crowtradingapp.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.deepakjetpackcompose.crowtradingapp.ui.screens.AppScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.HomeScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.LoginScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.OnBoardingScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.PaymentScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.ProfileWalletScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.SignUpScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.SuccessfulPaymentScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.SuccessfulWithdrawScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.TradingScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.WithdrawScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf<NavigationHelper?>(null) }

    LaunchedEffect(Unit) {
        val user = Firebase.auth.currentUser
        startDestination = if (user != null) {
            NavigationHelper.AppScreen
        } else {
            NavigationHelper.OnBoarding
        }
    }

    // Avoid rendering NavHost until destination is set
    startDestination?.let { start ->
        NavHost(navController = navController, startDestination = start) {
            composable<NavigationHelper.OnBoarding> { OnBoardingScreen(navController = navController) }
            composable<NavigationHelper.LoginScreen> { LoginScreen(navController = navController) }
            composable<NavigationHelper.SignUpScreen> { SignUpScreen(navController = navController) }
            composable<NavigationHelper.HomeScreen> { HomeScreen(navController = navController) }
            composable<NavigationHelper.AppScreen> { AppScreen(navControl = navController) }
            composable<NavigationHelper.TradingScreen> {
                val data = it.toRoute<NavigationHelper.TradingScreen>()
                val id = data.id
                TradingScreen(
                    id=id?:"",
                    symbol = data.symbol?:"",
                    name=data.name?:"",
                    balance = data.balance?:0.0,
                    currentPrice = data.current_price?:0.0,
                    percentage = data.price_change_percentage_24h?:0.0,
                    image = data.image?:"",
                    price = data.price?:emptyList(),
                    price_change_24h = data.price_change_24h?:0.0,
                    navController = navController
                )
            }

            composable<NavigationHelper.PaymentScreen> { PaymentScreen(navController = navController) }
            composable<NavigationHelper.SuccessfulPaymentScreen> { SuccessfulPaymentScreen(navController = navController) }

            composable<NavigationHelper.WithdrawScreen> {
                val data=it.toRoute<NavigationHelper.WithdrawScreen>()
                WithdrawScreen(balance = data.balance, navController = navController)
            }
            composable<NavigationHelper.SuccessfulWithdrawScreen> { SuccessfulWithdrawScreen(navController = navController) }

            composable <NavigationHelper.ProfileWalletScreen>{ ProfileWalletScreen(navController = navController) }
        }
    }
}