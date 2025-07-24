package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun SellDialogComponent(
    image: String,
    name: String,
    currentPrice: Double,
    boughtPrice: Double,
    boughtCoinCount: Int, // 👈 NEW PARAMETER
    onCancel: () -> Unit,
    onSell: (Int) -> Unit,
    symbol: String
) {
    var coinCount by remember { mutableIntStateOf(1) }
    val profitOrLoss = ((currentPrice - boughtPrice) / boughtPrice) * 100
    val totalSell = (currentPrice * coinCount).let {
        BigDecimal(it).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    val profitColor = if (profitOrLoss >= 0) Color(0xFF02C173) else Color(0xFFE11A38)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1E1E1E)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sell Coin", fontSize = 20.sp, color = Color.White)
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(10.dp))
                Text(name, fontSize = 18.sp, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            Text("Current Price: $${String.format("%.2f", currentPrice)}", color = Color.LightGray)
            Text("Bought Price: $${String.format("%.2f", boughtPrice)}", color = Color.LightGray)
            Text("Bought Coins: $boughtCoinCount", color = Color.LightGray)
            Text(
                "${String.format("%.2f", profitOrLoss)}%",
                color = profitColor,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (coinCount > 1) coinCount--
                }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                }

                Text(coinCount.toString(), fontSize = 18.sp, color = Color.White)

                IconButton(onClick = {
                    if (coinCount < boughtCoinCount) coinCount++
                }) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Total: $${String.format("%.2f", totalSell)}",
                color = profitColor,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(
                    onClick = onCancel,
                    border = BorderStroke(2.dp, Color.Red),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { onSell(coinCount) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF02C173)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sell")
                }
            }
        }
    }
}

