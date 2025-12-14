package com.App.healtcare.ui.feature.settings.presentation.question_settings._main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.App.healtcare.ui.feature.settings.component.ButtonSettings
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink

@Composable
fun QuestionSettings(
    toMath: ()-> Unit,
    toVocabullary: () -> Unit,
    toMasterSettings: () -> Unit,
    navController: NavController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(73.dp)
                .clip(RoundedCornerShape(
                    bottomStart = 11.dp,
                    bottomEnd = 11.dp
                ))
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
                    text = "Question Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 128.dp)
                .clip(RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                ))
                .background(color = Color.White)
        ) {
            Text(
                text = "Question",
                fontSize = 18.sp,
                color = GrayText

            )
            //quesiton
            ButtonSettings(
                text = "Math Settings",
                onClick = toMath,
                arrowButton = true,
                switchButton = false,
                isChecked = false,
                onCheckedChange = {}
            )
            ButtonSettings(
                text = "Vocabulary Settings",
                onClick = toVocabullary,
                arrowButton = true,
                switchButton = false,
                isChecked = false,
                onCheckedChange = {}
            )
            ButtonSettings(
                text = "Image Settings",
                onClick = {},
                arrowButton = true,
                switchButton = false,
                isChecked = false,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Other",
                fontSize = 18.sp,
                color = GrayText

            )
            ButtonSettings(
                text = "Quiz Master Settings",
                onClick = toMasterSettings,
                arrowButton = true,
                switchButton = false,
                isChecked = false,
                onCheckedChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSet(){
    QuestionSettings(
        toMath = {},
        toVocabullary = {},
        toMasterSettings = {},
        navController = rememberNavController()
    )
}
