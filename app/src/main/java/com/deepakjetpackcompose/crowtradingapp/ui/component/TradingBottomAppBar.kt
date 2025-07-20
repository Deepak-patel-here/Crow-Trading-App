package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.crowtradingapp.R
import com.deepakjetpackcompose.crowtradingapp.domain.model.BottomBarModel


@Composable
fun TradingBottomAppBar(isSelected:Int,onIsSelected:(Int)->Unit,modifier: Modifier = Modifier) {

    val bottomList = listOf<BottomBarModel>(
        BottomBarModel(img = R.drawable.home, name = "Home", id = 1),
        BottomBarModel(img = R.drawable.chart, name = "Chart", id = 2),
        BottomBarModel(img = R.drawable.char_fav, name = "Favorite", id = 3),
        BottomBarModel(img = R.drawable.walletme, name = "Wallet", id = 4)
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            bottomList.forEach { item ->
                IconButton(onClick = {onIsSelected(item.id)}) {
                    Icon(
                        painter = painterResource(item.img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = if (isSelected == item.id) Color(0xFFFFA532) else Color.Gray
                    )
                }
            }

        }
    }
}
