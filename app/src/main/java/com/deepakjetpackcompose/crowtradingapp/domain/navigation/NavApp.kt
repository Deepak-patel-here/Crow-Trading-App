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
import com.deepakjetpackcompose.crowtradingapp.ui.screens.AppScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.HomeScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.LoginScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.OnBoardingScreen
import com.deepakjetpackcompose.crowtradingapp.ui.screens.SignUpScreen
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
        }
    }
}