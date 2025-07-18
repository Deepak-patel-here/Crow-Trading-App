package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AuthTextComponent(text1:String, text2:String, onClick:()-> Unit,modifier: Modifier = Modifier) {

    Row (modifier = modifier){
        Text(text1,color =Color.White)
        Text(text2, modifier = Modifier.clickable(onClick=onClick), color =  Color(0xFFFFA532))
    }

}