package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.crowtradingapp.R

@Composable
fun SwipeComponent(
    onSwipeComplete: () -> Unit,
    modifier: Modifier = Modifier
) {

    val infiniteTransition = rememberInfiniteTransition(label = "arrowAnimation")

    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha1"
    )

    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha2"
    )

    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "alpha3"
    )
    val maxDragDistance = 750f
    var offsetX by remember { mutableStateOf(0f) }

    // Background with blur (glassmorphism)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(40.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        // Blurred background layer (BEHIND)
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

        // Foreground content (NOT BLURRED)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .offset { IntOffset(offsetX.toInt(), 0) }
                    .background(Color.White, shape = CircleShape)
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            offsetX = (offsetX + delta).coerceIn(0f, maxDragDistance)
                        },
                        onDragStopped = {
                            if (offsetX > maxDragDistance * 0.6f) {
                                onSwipeComplete()
                            }
                            offsetX = 0f
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.right_arrow),
                    contentDescription = "Swipe",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.size(24.dp)
                )
            }

            Text("Swipe to invest", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = alpha1),
                    modifier = Modifier.size(27.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = alpha2),
                    modifier = Modifier.size(30.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = alpha3),
                    modifier = Modifier.size(35.dp)
                )


            }
        }
    }
}
