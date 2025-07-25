package com.deepakjetpackcompose.crowtradingapp.domain.navigation

import androidx.navigation.Navigation
import com.deepakjetpackcompose.crowtradingapp.data.model.SparklineIn7d
import kotlinx.serialization.Serializable

sealed class NavigationHelper {

    @Serializable
    object OnBoarding: NavigationHelper()

    @Serializable
    object LoginScreen: NavigationHelper()

    @Serializable
    object SignUpScreen: NavigationHelper()

    @Serializable
    object HomeScreen : NavigationHelper()

    @Serializable
    object AppScreen: NavigationHelper()

    @Serializable
    data class TradingScreen(val symbol:String?=null,
                             val id:String?=null,
                             val current_price:Double?=null,
                             val price_change_percentage_24h:Double?=null,
                             val image:String?=null,
                             val name:String?=null,
                             val price: List<Double>?=null,
                             val price_change_24h: Double? = null,
                             val balance:Double?=null
    ): NavigationHelper()

    @Serializable
    object PaymentScreen: NavigationHelper()

    @Serializable
    object SuccessfulPaymentScreen: NavigationHelper()

    @Serializable
    data class WithdrawScreen(val balance:String): NavigationHelper()

    @Serializable
    object SuccessfulWithdrawScreen: NavigationHelper()

    @Serializable
    object ProfileWalletScreen: NavigationHelper()

}