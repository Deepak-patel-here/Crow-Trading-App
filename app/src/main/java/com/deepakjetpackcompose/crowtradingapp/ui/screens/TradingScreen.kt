package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.ui.component.CustomDropDownComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.GlassMorphismIconComponent

@Preview
@Composable
fun TradingScreen(modifier: Modifier = Modifier) {
    var isFav by remember{ mutableStateOf(false) }
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
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            GlassMorphismIconComponent(
                size = 50.dp,
                image = R.drawable.back_trading,
                color = Color.White,
                imgSize = 24.dp,
                onClick = {})

            Text("ETH / USDT", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            GlassMorphismIconComponent(
                size = 50.dp,
                image =if(!isFav)R.drawable.star_trading else R.drawable.star_filled_trading,
                color = Color.White,
                imgSize = 24.dp,
                onClick = {isFav = !isFav})
        }

        Spacer(Modifier.height(20.dp))

        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("$1,150.00" ,fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(6.dp))
                Text("0.89%",fontSize = 16.sp, color = Color.Green)
            }

            Column(horizontalAlignment = Alignment.End) {
                CustomDropDownComponent(text = "1 hour", height = 29.dp, width = 100.dp)
                Spacer(Modifier.height(6.dp))
                CustomDropDownComponent(text = "Indicator", height = 29.dp, width = 120.dp)
            }
        }

        Spacer(Modifier.height(25.dp))


    }

}