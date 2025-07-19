package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem

@Composable
fun CryptoShowComponent(coinModel: CryptoModelItem, onClick:()->Unit,modifier: Modifier = Modifier) {
    val color=if(coinModel.price_change_percentage_24h!!>0) Color(0xFF02C173) else Color(0xFfE11A38)
    val data=coinModel.sparkline_in_7d?.price?:emptyList()
    val percentage=toPercentage(coinModel.price_change_percentage_24h,100.00)
    Row (modifier = modifier
        .fillMaxWidth()
        .height(80.dp),
        verticalAlignment = Alignment.CenterVertically){
        AsyncImage(model = coinModel.image,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop)

        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxHeight().width(60.dp), verticalArrangement = Arrangement.Center) {
            Text(coinModel.symbol?.toUpperCase()?:"Unk", fontSize = 16.sp, color = Color.White)
            Text(coinModel.name?:"Unknown", fontSize = 14.sp, color = Color.LightGray, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
        Spacer(Modifier.weight(1f))
        CryptoMiniGraph(data = data, lineColor = color, modifier = Modifier
            .height(42.dp)
            .width(100.dp))

        Spacer(Modifier.weight(1f))

        Column(modifier = Modifier.fillMaxHeight().width(80.dp), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.End) {
            Text("$"+coinModel.current_price.toString(), fontSize = 16.sp, color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 1, textAlign = TextAlign.End)
            Text("$percentage", fontSize = 14.sp, color = color, textAlign = TextAlign.End)
        }

    }

}

fun toPercentage(value: Double, total: Double): String {
    if (total == 0.0) return "0.0%" // avoid divide-by-zero
    val percentage = (value / total) * 100
    return String.format("%.1f%%", percentage)
}


