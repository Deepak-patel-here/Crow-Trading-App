package com.deepakjetpackcompose.crowtradingapp.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepakjetpackcompose.crowtradingapp.ui.screens.LoginScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.OnBoardingScreen

@Composable
fun NavApp(modifier: Modifier = Modifier) {

    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = NavigationHelper.OnBoarding) {
        composable <NavigationHelper.OnBoarding>{ OnBoardingScreen(navController = navController) }
        composable <NavigationHelper.LoginScreen>{ LoginScreen(navController = navController) }
    }

}