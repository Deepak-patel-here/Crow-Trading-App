package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@Preview
@Composable
fun FavoriteCoinComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .width(163.dp)
            .height(191.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color.Yellow.copy(alpha = 0.03f), // Start yellow
                        0.5f to Color(0xFF25231a).copy(alpha = 0.5f)   // Start blending into black at 20%
                    )
                )
            ).padding(20.dp)
    ) {
        Row (modifier = Modifier.width(100.dp).height(42.dp)){
            AsyncImage(model="",
                contentDescription = null,
                modifier=Modifier.size(32.dp).clip(CircleShape),
                contentScale = ContentScale.Crop)

            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text("BTC", fontSize = 16.sp, color = Color.White)
                Text("Bitcoin", fontSize = 14.sp, color = Color.LightGray, overflow = TextOverflow.Ellipsis, maxLines = 1)
            }

            CryptoMiniGraph(data=emptyList(), lineColor = Color.Red
                , modifier = Modifier.height(42.dp).fillMaxWidth())
        }

        Spacer(Modifier.height(20.dp))
        Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
            Text("$15,240", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text("0.25%", fontSize = 16.sp, color = Color.Red)
        }
        Spacer(Modifier.height(2100.dp))

    }
}
