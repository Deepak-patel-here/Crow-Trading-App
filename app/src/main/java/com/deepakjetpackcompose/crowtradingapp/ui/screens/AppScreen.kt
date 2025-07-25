package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepakjetpackcompose.crowtradingapp.ui.component.TradingBottomAppBar
import com.deepakjetpackcompose.crowtradingapp.ui.component.TradingTopAppBar
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel

@Composable
fun AppScreen(navControl: NavController,modifier: Modifier = Modifier,authViewModel: AuthViewModel= hiltViewModel<AuthViewModel>()) {
    val isSelected= authViewModel.isSelected
    Scaffold (
        topBar = {
            TradingTopAppBar(onClick1 = {isSelected.intValue=4}, modifier = modifier.statusBarsPadding())
        },

        bottomBar = {
            Row(modifier = Modifier.navigationBarsPadding().padding(horizontal = 16.dp, vertical = 8.dp)) {
                TradingBottomAppBar(
                    isSelected = isSelected.intValue,
                    onIsSelected = { isSelected.intValue = it })
            }
        }
    ){innerPadding->

        when(isSelected.intValue){
            1-> HomeScreen(navController = navControl, modifier = Modifier.padding(innerPadding))
            2->AllCoinScreen(navController = navControl,modifier = Modifier.padding(innerPadding))
            3->FavoriteCoinScreen(navController = navControl, modifier = Modifier.padding(innerPadding))
            4-> ProfileWalletScreen(navController = navControl, modifier = Modifier.padding(innerPadding))
        }

    }

}