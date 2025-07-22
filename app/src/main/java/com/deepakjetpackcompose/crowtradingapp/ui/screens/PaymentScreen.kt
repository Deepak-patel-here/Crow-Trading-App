package com.deepakjetpackcompose.crowtradingapp.ui.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.navigation.NavigationHelper
import com.deepakjetpackcompose.crowtradingapp.ui.component.CoinLoader
import com.deepakjetpackcompose.crowtradingapp.ui.component.GlassMorphismIconComponent
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.AuthViewModel
import com.deepakjetpackcompose.crowtradingapp.ui.viewmodels.UpdateBalance
import kotlin.math.max

@Composable
fun PaymentScreen(navController: NavController, modifier: Modifier = Modifier,authViewModel: AuthViewModel= hiltViewModel<AuthViewModel>()) {
    val amount= remember{ mutableStateOf("$ ") }
    val context= LocalContext.current
    val isLoading=authViewModel.loading.collectAsState()

    LaunchedEffect(isLoading.value) {
        if(isLoading.value== UpdateBalance.Success){
            navController.navigate(NavigationHelper.SuccessfulPaymentScreen){
                popUpTo(NavigationHelper.PaymentScreen){
                    inclusive=true
                }
            }
        }
    }
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
            .navigationBarsPadding()
    )
    {
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
                onClick = {
                    navController.popBackStack()
                }
            )
        }

        Spacer(Modifier.height(30.dp))

        Text("Please Enter the Amount for Deposit.",  fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(30.dp))

        TextField(
            value = amount.value,
            onValueChange = {amount.value=it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                ,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFEF6C00),
                unfocusedIndicatorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true,
            maxLines = 1,
            minLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {

            authViewModel.updateAccount(amount = amount.value.substring(1), sign = "+"){
                success,msg->
               Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

            }
        }, modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Deposit", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.padding(bottom = 30.dp))






    }
    if(isLoading.value== UpdateBalance.Loading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f)), // semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CoinLoader(size = 300.dp)
        }
    }
}

@Preview
@Composable
private fun PaymentPrev() {
    PaymentScreen(navController = rememberNavController())

}
