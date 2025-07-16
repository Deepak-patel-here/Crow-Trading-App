package com.deepakjetpackcompose.crowtradingapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.crowtradingapp.ui.component.AuthTextField
import com.deepakjetpackcompose.crowtradingapp.ui.component.CustomCircleComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.LottieExample

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {

    val emailIn = remember { mutableStateOf("") }
    val passwordIn = remember { mutableStateOf("") }
    val passwordFocus = remember { FocusRequester() }
    val keyboard= LocalSoftwareKeyboardController.current
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
            val (c1, c2, c3, c4, c5, c6, coin, email, password, loginBtn, otherText) = createRefs()

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
                    top.linkTo(parent.top, margin = 280.dp)
                    end.linkTo(parent.end, margin = (-200).dp)
                })

            CustomCircleComponent(
                size = 250f,
                color = Color.Yellow,
                offSetX = 300f,
                offSetY = 200f,
                modifier = Modifier.constrainAs(c4) {
                    top.linkTo(parent.top, margin = 270.dp)
                    end.linkTo(parent.end, margin = (-180).dp)
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

            LottieExample(size = 150.dp, modifier = Modifier.constrainAs(coin) {
                top.linkTo(parent.top, margin = 120.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            AuthTextField(
                input = emailIn.value,
                onChange = { emailIn.value = it },
                label = "Email",
                trailingIcon = Icons.Default.Email,
                isPassword = false,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                modifier = Modifier.constrainAs(email) {
                    top.linkTo(coin.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            AuthTextField(
                input = passwordIn.value,
                onChange = { passwordIn.value = it },
                label = "Password",
                trailingIcon = Icons.Default.Lock,
                isPassword = false,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onNext = { keyboard?.hide() }),
                modifier = Modifier.constrainAs(password) {
                    top.linkTo(email.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

        }
    }


}