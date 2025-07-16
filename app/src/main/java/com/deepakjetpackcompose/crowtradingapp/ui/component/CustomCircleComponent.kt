package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircleComponent(size:Float,color:Color,offSetX:Float,offSetY:Float    ,modifier: Modifier = Modifier) {

    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = color.copy(alpha = 0.08f),
            radius = size,
            center = Offset(offSetX, offSetY),
            style = Stroke(width = 3.dp.toPx(),)

        )}

}