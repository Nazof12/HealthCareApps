package com.App.healtcare.ui.feature.settings.presentation.question_settings._main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
fun QuestionSettings(
    toMath: ()-> Unit,
    toVocabullary: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row{
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "back"
                    )
                }
                Text("Question")
            }
            //math quesiton
            Button(
                onClick = toMath
            ) {
                Text("Math")
            }
            //vocabulary
            Button(
                onClick = toVocabullary
            ){
                Text("Vocabullary")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSet(){
    QuestionSettings(
        toMath = {},
        toVocabullary = {}
    )
}
