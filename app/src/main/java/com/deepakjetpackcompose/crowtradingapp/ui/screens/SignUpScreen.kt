package com.deepakjetpackcompose.crowtradingapp.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.AuthTextComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.AuthTextField
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.CustomCircleComponent
import com.deepakjetpackcompose.crowtradingapp.ui.component.LottieExample
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthState
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
) {
    val emailIn = remember { mutableStateOf("") }
    val passwordIn = remember { mutableStateOf("") }
    val nameIn = remember { mutableStateOf("") }
    val confirmPasswordIn = remember { mutableStateOf("") }
    val passwordFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val confirmPasswordFocus = remember { FocusRequester() }
    val authState = viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        if (authState.value == AuthState.Success) {
            navController.navigate(NavigationHelper.HomeScreen) {
                popUpTo(NavigationHelper.LoginScreen) { inclusive = true }
            }
        }
    }

    val keyboard = LocalSoftwareKeyboardController.current
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
            .statusBarsPadding()

    ) {
        val screenHeight = minHeight
        val screenWidth = minWidth
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (c1, c2, c3, c4, c5, c6, coin, email, password, name, cPassword, loginBtn, otherText) = createRefs()

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
                input = nameIn.value,
                onChange = { nameIn.value = it },
                label = "Name",
                trailingIcon = Icons.Default.Person,
                isPassword = false,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() }),
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(coin.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            AuthTextField(
                input = emailIn.value,
                onChange = { emailIn.value = it },
                label = "Email",
                trailingIcon = Icons.Default.Email,
                isPassword = false,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                modifier = Modifier
                    .constrainAs(email) {
                        top.linkTo(name.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .focusRequester(emailFocus)
            )

            AuthTextField(
                input = passwordIn.value,
                onChange = { passwordIn.value = it },
                label = "Password",
                trailingIcon = Icons.Default.Lock,
                isPassword = true,
                show = R.drawable.show,
                hide = R.drawable.hide,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { confirmPasswordFocus.requestFocus() }),
                modifier = Modifier
                    .constrainAs(password) {
                        top.linkTo(email.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .focusRequester(passwordFocus)
            )

            AuthTextField(
                input = confirmPasswordIn.value,
                onChange = { confirmPasswordIn.value = it },
                label = "Re-enter passoword here",
                trailingIcon = Icons.Default.Lock,
                isPassword = true,
                show = R.drawable.show,
                hide = R.drawable.hide,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onNext = { keyboard?.hide() }),
                modifier = Modifier
                    .constrainAs(cPassword) {
                        top.linkTo(password.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .focusRequester(confirmPasswordFocus)
            )

            Button(
                onClick = {
                    if (emailIn.value.isNotBlank() && passwordIn.value.isNotBlank() && passwordIn.value.isNotBlank() && confirmPasswordIn.value.isNotBlank()) {

                        if(passwordIn.value!=confirmPasswordIn.value) Toast.makeText(
                            context,
                            "password not matches",
                            Toast.LENGTH_SHORT
                        ).show()
                        else {
                            viewModel.register(
                                email = emailIn.value,
                                password = passwordIn.value,
                                name=nameIn.value
                            ) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else Toast.makeText(context, "Fill all the fiels", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .constrainAs(loginBtn) {
                        top.linkTo(cPassword.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA532)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SignUp", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            AuthTextComponent(
                text1 = "Already have an account? ",
                text2 = "Login",
                onClick = {
                    navController.navigate(NavigationHelper.LoginScreen) {
                        popUpTo(NavigationHelper.SignUpScreen) { inclusive = true }
                    }
                },
                modifier = Modifier.constrainAs(otherText) {
                    top.linkTo(loginBtn.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })


        }
    }

    if (authState.value == AuthState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f)), // semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CoinLoader(size = 250.dp)
        }
    }

}