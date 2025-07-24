package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.deepakjetpackcompose.crowtradingapp.R

@Composable
fun BuyCoinDialog(
    image: String,
    name: String,
    price: Double,
    balance: Double,
    onBuy: (Int, Double) -> Unit,
    onCancel: () -> Unit,
    symbol: String
) {
    val coinCount = remember { mutableIntStateOf(1) }
    val totalPayable = price * coinCount.intValue
    val isAffordable = totalPayable <= balance
    val totalColor = if (isAffordable) Color(0xFF02C173) else Color(0xFFF44336) // Green or Red

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1C1C1E),
        tonalElevation = 8.dp,
        shadowElevation = 10.dp,
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))
            Text(name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Price: $${price}", color = Color.Gray, fontSize = 14.sp)

            Spacer(Modifier.height(20.dp))

            // Stepper
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (coinCount.intValue > 1) coinCount.intValue-- },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.DarkGray, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }

                Text(
                    coinCount.intValue.toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                IconButton(
                    onClick = { coinCount.intValue++ },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.DarkGray, CircleShape)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Total Payable Amount
            Text(
                "Total: $${String.format("%.2f", totalPayable)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = totalColor
            )

            Spacer(Modifier.height(24.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel", color = Color.White)
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = { onBuy(coinCount.intValue, totalPayable) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF02C173)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f),
                    enabled = isAffordable
                ) {
                    Text("Buy", color = Color.White)
                }
            }
        }
    }
}

