package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.crowtradingapp.R


@Preview
@Composable
fun TradingTopAppBar(modifier: Modifier = Modifier) {

    Row (modifier = modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){

        GlassMorphismIconComponent(size = 50.dp,image=R.drawable.profile, color = Color.White, imgSize = 24.dp, onClick = {})
        GlassMorphismIconComponent(size = 50.dp,image=R.drawable.bell, color = Color.White, imgSize = 24.dp, onClick = {})
    }

}