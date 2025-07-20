package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deepakjetpackcompose.crowtradingapp.ui.component.TradingBottomAppBar
import com.deepakjetpackcompose.crowtradingapp.ui.component.TradingTopAppBar
import com.google.api.Control
import javax.annotation.meta.When

@Composable
fun AppScreen(navControl: NavController,modifier: Modifier = Modifier) {
    val isSelected= remember{ mutableIntStateOf(1) }
    Scaffold (
        topBar = {
            TradingTopAppBar(modifier = modifier.statusBarsPadding())
        },

        bottomBar = {
            Row(modifier = Modifier.navigationBarsPadding().padding(horizontal = 16.dp, vertical = 8.dp)) {
                TradingBottomAppBar(
                    isSelected = isSelected.intValue,
                    onIsSelected = { isSelected.intValue = it })
            }
        }
    ){innerPadding->

        when(isSelected.value){
            1-> HomeScreen(navController = navControl, modifier = Modifier.padding(innerPadding))
            3->FavoriteCoinScreen(navController = navControl, modifier = Modifier.padding(innerPadding))
        }

    }

}