package com.App.healtcare.ui.feature.settings.presentation._main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    toCheckeApp: () -> Unit,
    toQuestion: () -> Unit

    ){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .offset(x = (-16.dp))
            ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "back"
                    )
            }
            Text(
                text = "Settings",
            )
            Button(
                onClick = toCheckeApp
            ){
                Text("App Check")
            }
            Button(
                onClick = toQuestion
            ){
                Text("Question")
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen(
        toCheckeApp = {},
        toQuestion = {}
    )
}
