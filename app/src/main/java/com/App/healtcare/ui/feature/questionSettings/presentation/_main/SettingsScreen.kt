package com.App.healtcare.ui.feature.questionSettings.presentation._main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.App.healtcare.ui.feature.questionSettings.component.ButtonSettings
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink

@Composable
fun SettingsScreen(
    toCheckeApp: () -> Unit,
    toQuestion: () -> Unit,
    toExtraSettings: () -> Unit
    ){
    Box(
        modifier = Modifier
            .fillMaxSize()

    ){

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(294.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .background(MyPink)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .padding(start = 16.dp)
                    .offset(-4.dp)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        tint = Color.White,
                        contentDescription = "back",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
                Text(
                    text = "Settings",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier

                )
            }
        }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 128.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .background(Color.White)
            ) {

                //Account name
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 25.dp)
                        .offset(-4.dp),
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(40.dp),
                        tint = MyPink,
                    )
                    Text(
                        text ="Nazof Ilman Hasan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .padding(top = 24.dp)
                ){

                    //Start Account Settings Section
                    Text(
                        text="Account Settings",
                        color = GrayText,
                        fontSize = 18.sp
                    )
                    ButtonSettings(
                        text = "Edit Profile",
                        onClick = {},
                        arrowButton = true,
                        switchButton = false,
                        isChecked = true,
                        onCheckedChange = {  }
                    )
                    ButtonSettings(
                        text = "Change Password",
                        onClick = {},
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = {},
                    )
                    // End Account Settings Section
                    //Start General Settings
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        text = "General",
                        fontSize = 18.sp,
                        color = GrayText
                    )
                    ButtonSettings(
                        text = "Manage Question",
                        onClick = toQuestion,
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = {}
                    )
                    ButtonSettings(
                        text = "App Hooks",
                        onClick = toCheckeApp,
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = {}
                    )
                    ButtonSettings(
                        text = "Extra Settings",
                        onClick = toExtraSettings,
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = { }
                    )
                    ButtonSettings(
                        text = "Block Notification",
                        onClick = {},
                        arrowButton = false,
                        switchButton = true,
                        isChecked = true,
                        onCheckedChange = {}
                    )

                    // End General Settings
                    // Start More settings
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        text = "More",
                        fontSize = 18.sp,
                        color = GrayText
                    )
                    ButtonSettings(
                        text = "About us",
                        onClick = {},
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = {}
                    )
                    ButtonSettings(
                        text = "Privacy policy",
                        onClick = {},
                        arrowButton = true,
                        switchButton = false,
                        isChecked = false,
                        onCheckedChange = {}
                    )
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen(
        toCheckeApp = {},
        toQuestion = {},
        toExtraSettings = {}
    )
}
