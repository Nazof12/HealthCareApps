package com.App.healtcare.sevice

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.data.repository.TimeSetup
import com.App.healtcare.ui.feature.extraSettings.main.domain.ExtraViewmodel
import kotlinx.coroutines.delay

@Composable
fun TimerServicesUI(
    initialMinute: Long,
    onTimeUp: () -> Unit
){
   var timeRemaining by remember { mutableLongStateOf(initialMinute * 60000L)}

        LaunchedEffect(Unit) {
            while (timeRemaining > 0){
                delay(1000)
                timeRemaining -= 1000
            }
            onTimeUp()
        }

    Surface(
            modifier = Modifier.padding(16.dp),
            color = Color.Black.copy(alpha = 0.6f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = formatTime(timeRemaining),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        }


}
fun formatTime(ms: Long): String{
    val totalSecond = ms/1000
    val minutes = totalSecond / 60
    val seconds = totalSecond % 60
    return String.format("%02d:%02d", minutes, seconds)
}