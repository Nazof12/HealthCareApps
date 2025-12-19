package com.App.healtcare.ui.feature.extraSettings.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.App.healtcare.ui.feature.extraSettings.main.domain.ExtraViewmodel
import com.App.healtcare.ui.feature.questionSettings.component.ButtonMathSettings
import com.App.healtcare.ui.feature.questionSettings.component.ButtonSettings
import com.App.healtcare.ui.feature.questionSettings.component.ValueInputDialog
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink

@Composable
fun ExtraSettings(
    navController: NavController,
    extraViewmodel: ExtraViewmodel = hiltViewModel()
){
    val uiTime by extraViewmodel.getAppTimer.collectAsStateWithLifecycle()
    var showSetTimer by remember {mutableStateOf(false)}
    var timerApp by remember(uiTime.longTime) {mutableStateOf(uiTime.longTime.toString())}
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(73.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 11.dp,
                        bottomEnd = 11.dp
                    )
                )
                .background(MyPink)
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "question settings",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(
                            enabled = true,
                            onClick = {
                                navController.navigateUp()
                            }
                        )
                )
                Text(
                    text = "Extra Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 128.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .background(color = Color.White)
        ) {

            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text="Timer Setting",
                color = GrayText,
                fontSize = 18.sp
            )
           ButtonSettings(
               text = "Turn On Timer",
               onClick = {},
               arrowButton = false,
               switchButton = true,
               isChecked = uiTime.isChecked,
               onCheckedChange = {isChecked ->
                   extraViewmodel.saveAppTime(uiTime.longTime, isChecked)
               }
           )
            Spacer(modifier = Modifier.height(28.dp))
            val customColor = if(uiTime.isChecked) Color.Black else Color.Gray
            CompositionLocalProvider(
                LocalContentColor provides customColor
            ) {
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable(
                       enabled = uiTime.isChecked,
                       onClick = {
                           showSetTimer = true
                       }
                   )
           ) {
               ButtonMathSettings(
                   textHeader = "Set Timer",
                   textSemi = "${uiTime.longTime} Minute",
                   onClick = {
                       if(uiTime.isChecked) showSetTimer = true
                   }
               )
               if(showSetTimer){
                   AlertDialog(
                       onDismissRequest = {
                           showSetTimer = false
                       },
                       title = {
                           Text(text = "Change Time")
                       },
                       text = {
                           Column {
                               Text("Enter a new value : ")
                               Spacer(modifier = Modifier.height(8.dp))
                               OutlinedTextField(
                                   value = timerApp,
                                   onValueChange = {
                                       if (it.all {char -> char.isDigit()}){
                                           timerApp = it
                                       }
                                   },
                                   singleLine = true,
                                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                               )
                           }
                       },
                       confirmButton = {
                           TextButton(
                               onClick = {
                                   val timer = timerApp.toIntOrNull() ?: 0
                                   extraViewmodel.saveAppTime(timer, uiTime.isChecked)
                                   showSetTimer = false
                               }
                           ) {
                               Text("OK")
                           }
                       },
                       dismissButton = {
                           TextButton(
                               onClick = {
                                   showSetTimer = false
                               }
                           ) {
                               Text("Cancel")
                           }
                       }
                   )
               }
//                  ValueInputDialog(
//                      title = "Change Time",
//                      initialValue = timerApp,
//                      onDismissRequest = {
//                          showSetTimer = false
//                      },
//                      onConfirm = { values ->
//                          val timer = values.toIntOrNull() ?: 0
//                          extraViewmodel.saveAppTime(timer, uiTime.isChecked)
//                          timerApp = timer.toString()
//                          showSetTimer = false
//                      }
//                  )
//               }
           }
            }
        }
    }
}

@Preview
@Composable
fun Previews(){

}