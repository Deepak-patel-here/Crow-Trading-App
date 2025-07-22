package com.deepakjetpackcompose.crowtradingapp.ui.component

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun BuyComponent(image: String, name:String,symbol:String,currentPrice:Double,balance:Double,modifier: Modifier = Modifier) {
    val coinCnt = remember { mutableIntStateOf(0) }
    val totalAmount=remember { mutableStateOf(0.00) }
    val color=if(balance>totalAmount.value)Color.Green else Color.Red

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(16.dp)) // ✅ solid background
            .padding(20.dp) // add padding to content
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Buy Coins", color = Color.White)
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(10.dp))

                Text("$name ($symbol)",color = Color.White)

                Spacer(Modifier.weight(1f))
                Text(coinCnt.intValue.toString(), color = Color.White)
                Spacer(Modifier.width(10.dp))
                Column {
                    IconButton(onClick = {
                        coinCnt.intValue++
                        totalAmount.value+= coinCnt.intValue*currentPrice
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    IconButton(onClick = {
                        if (coinCnt.intValue > 0) {

                            coinCnt.intValue--
                            totalAmount.value-= coinCnt.intValue*currentPrice
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Text("Total Amount : ${totalAmount.value}",color=color, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(Modifier.height(40.dp))

            Row {
                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(width = 2.dp, color = Color(0xFfE11A38)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel", color = Color.White)
                }

                Spacer(Modifier.width(20.dp))

                TransactionButton(title = "Buy", onClick = {}, color = Color(0xFF02C173))
            }
        }
    }
}


