package com.App.healtcare.ui.feature.questionSettings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.App.healtcare.ui.theme.MyPink

@Composable
fun ButtonSettings(
    text: String,
    onClick: () -> Unit,
    arrowButton: Boolean,
    switchButton: Boolean,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit

){
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClick
            ),

        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = text,
            modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal


        )
        if(arrowButton){
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = onClick
                    ),
                contentDescription = "button"
            )
        }
        if(switchButton){
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MyPink,
                    uncheckedThumbColor = MyPink,
                    uncheckedTrackColor = Color.LightGray
                ),
                modifier = Modifier
                    .width(56.dp)
                    .height(29.dp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun Preview(){
    ButtonSettings(
        text = "hello world",
        onClick = {},
        arrowButton = false,
        switchButton = true,
        isChecked = true,
        onCheckedChange = {},
    )
}
