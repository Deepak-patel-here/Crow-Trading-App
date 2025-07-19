package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun CryptoShowComponent(modifier: Modifier = Modifier) {

    Row (modifier = Modifier.fillMaxWidth().height(74.dp)){
        AsyncImage(model = "",
            contentDescription = null,
            modifier = Modifier.size(32.dp).clip(CircleShape),
            contentScale = ContentScale.Crop)

        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxHeight()) {
            Text("DOT", fontSize = 16.sp, color = Color.White)
            Text("polkadot", fontSize = 14.sp, color = Color.LightGray)
        }
        Spacer(Modifier.width(20.dp))


    }

}