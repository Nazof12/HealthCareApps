package com.App.healtcare.ui.feature.questionSettings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.App.healtcare.ui.theme.GrayText

@Composable
fun ButtonMathSettings(
    textHeader: String,
    textSemi: String,
    onClick:  () -> Unit
){
    val contentColor = LocalContentColor.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,


    ){
        Column(
        ) {
            Text(
                text = textHeader,
                fontSize = 18.sp,
                color = contentColor
            )
            Text(
                text = textSemi,
                fontSize = 16.sp,
                color = GrayText,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "button"

        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previews(){
    ButtonMathSettings(
        textHeader = "Min Number",
        textSemi = "10",
        onClick = {},
    )
}