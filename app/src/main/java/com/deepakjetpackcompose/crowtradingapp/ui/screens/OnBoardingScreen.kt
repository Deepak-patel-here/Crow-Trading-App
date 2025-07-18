package com.deepakjetpackcompose.crowtradingapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.CustomCircleComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.SwipeComponent

@SuppressLint("UnusedBoxWithConstraintsScope")


@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, navController: NavController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFEF6C00),
                        Color(0xFF3E2723),
                        Color(0xFF000000)
                    ),
                    center = Offset(800f, 0f),
                    radius = 1200f
                )
            )
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
            .statusBarsPadding()

    ) {
        val screenHeight = minHeight
        val screenWidth = minWidth
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (c1, c2, c3, c4,c5,c6, img, titleText, subtitle, swipe) = createRefs()

            CustomCircleComponent(
                size = 300f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c1) {
                    top.linkTo(parent.top, margin = 170.dp)
                    start.linkTo(parent.start, margin = (-40).dp)
                })

            CustomCircleComponent(
                size = 250f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c2) {
                    top.linkTo(parent.top, margin = 170.dp)
                    start.linkTo(parent.start, margin = (-55).dp)
                })

            CustomCircleComponent(
                size = 300f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c3) {
                    top.linkTo(img.bottom, margin = (-90).dp)
                    end.linkTo(parent.end, margin = (-180).dp)
                })

            CustomCircleComponent(
                size = 250f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c4) {
                    top.linkTo(img.bottom, margin = (-100).dp)
                    end.linkTo(parent.end, margin = (-160).dp)
                })

            CustomCircleComponent(
                size = 300f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c5) {
                    top.linkTo(parent.bottom, margin = (-90).dp)
                    end.linkTo(parent.end, margin = (-130).dp)
                })

            CustomCircleComponent(
                size = 250f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c6) {
                    top.linkTo(parent.bottom, margin = (-100).dp)
                    end.linkTo(parent.end, margin = (-150).dp)
                })


            Image(
                painter = painterResource(R.drawable.wallet_card_img),
                contentDescription = null,
                modifier = Modifier
                    .size(350.dp)
                    .constrainAs(img) {
                        top.linkTo(parent.top, margin = 170.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    })

            Text(
                "Buy,  sell  &  trade  more  efficient",
                textAlign = TextAlign.Start,
                fontSize = 40.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(img.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                },
                lineHeight = 40.sp)

            Text(
                "Instantly invest in cryptocurrencies, exchange it and pay online with us",
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,

                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(titleText.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                })

            SwipeComponent(onSwipeComplete = {navController.navigate(NavigationHelper.LoginScreen){
                popUpTo(NavigationHelper.OnBoarding){inclusive=true}
            } }, modifier = Modifier.constrainAs(swipe) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                start.linkTo(parent.start)
            })

        }
    }
}