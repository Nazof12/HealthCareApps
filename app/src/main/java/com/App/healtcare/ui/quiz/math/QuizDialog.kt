package com.App.healtcare.ui.quiz.math

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun QuizDialog(
    state: QuizUiState,
    onSubmitAnswer: (String) -> Unit,
    onDismiss: () -> Unit
){
    var answerText by remember { mutableStateOf("") }
    val question = state.currentQuestion

    // don't show dialog if the question doesn't ready yet
    if(question == null){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Loading question...", modifier = Modifier.padding(top = 40.dp))
        }
        return
    }

    // if the answer correct, the call onDismiss to close dialog
    if(state.isAnswerCorrect == true) {
        LaunchedEffect(Unit){
            onDismiss
        }
        return
    }

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "just a minute")
        },
        text = {
            Column{
                Text(
                    text = "solve this to continue: ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${question.questionText} = ?",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = answerText,
                    onValueChange = {answerText = it},
                    label = {Text("Answer")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = state.isAnswerCorrect == false
                )
                if (state.isAnswerCorrect == false){
                    Text(
                        text = "Wrong answer, try again!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmitAnswer(answerText)},
                enabled = answerText.isNotEmpty()
            ) {
                Text("Unlock the App")
            }
        }
    )
}