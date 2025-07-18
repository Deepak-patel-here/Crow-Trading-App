package com.deepakjetpackcompose.crowtradingapp.domain.navigation

import androidx.navigation.Navigation
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
}