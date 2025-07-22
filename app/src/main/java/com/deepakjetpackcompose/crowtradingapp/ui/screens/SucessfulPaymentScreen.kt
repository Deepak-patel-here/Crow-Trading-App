package com.deepakjetpackcompose.crowtradingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper

@Composable
fun SuccessfulPaymentScreen(navController: NavController, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = R.raw.success)
    )

    val progress by animateLottieCompositionAsState(
        composition
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFEF6C00).copy(alpha = 0.7f),
                        Color(0xFF3E2723).copy(alpha = 1f),
                        Color(0xFF161514)
                    ),
                    center = Offset(500f, 0f),
                    radius = 600f
                )
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            text = "Payment",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Successful",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(30.dp))



        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = modifier.size(250.dp))

        Spacer(Modifier.weight(1f))

        Button(onClick = {
            navController.navigate(NavigationHelper.HomeScreen){
                popUpTo(NavigationHelper.SuccessfulPaymentScreen){inclusive=true}
            }
        }, modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 15.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4511E))
        ) {
            Text("Back to Home",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
        }
    }
}