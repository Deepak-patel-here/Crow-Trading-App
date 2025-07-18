package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.crowtradingapp.R

@Composable
fun GlassMorphismIconComponent(size: Dp, image:Int, imgSize:Dp, color:Color, onClick:()-> Unit,modifier: Modifier = Modifier) {

    Box(modifier  = Modifier
        .size(size)
        .background(Color.Transparent)
        .clip(RoundedCornerShape(12.dp))
        .clickable(onClick =onClick ),
        contentAlignment = Alignment.Center){
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Gray.copy(alpha = 0.1f))
                .blur(20.dp)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.1f)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        )

        Image(painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(imgSize),
            colorFilter = ColorFilter.tint(color))
    }

}