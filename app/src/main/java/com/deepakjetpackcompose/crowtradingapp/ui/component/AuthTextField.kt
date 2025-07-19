package com.deepakjetpackcompose.crowtradingapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(
    input: String,
    onChange: (String) -> Unit,
    label: String,
    trailingIcon: ImageVector,
    isPassword: Boolean,
    hide: Int?=null,
    show: Int?=null,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier
) {
    var isShow by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = input,
        onValueChange = { onChange(it) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = Color(0xFFFFA532)
            )
        },
        visualTransformation = if (isShow)  VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isShow = !isShow }) {
                    Icon(
                        painter = painterResource(if (isShow) show!! else hide!!),
                        contentDescription = null,
                        tint=Color(0xFFFFA532),
                        modifier=Modifier.size(24.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction =imeAction),
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFFFA532),
            focusedLabelColor =  Color(0xFFFFA532),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        )

    )

}