package com.App.healtcare.ui.feature.settings.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WordDialogButton(
    title: String,
    label1: String,
    label2: String,
    front: String,
    back: String,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
){
    var question by remember { mutableStateOf(front) }
    var answer by remember {mutableStateOf(back)}
    AlertDialog(
       onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = question,
                    onValueChange = {question = it},
                    label = {Text(label1)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = answer,
                    onValueChange = {answer = it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = {Text(label2)},
                    singleLine = true,
                )
            }
        },
        confirmButton = {
            TextButton(
              onClick = {
                  onConfirm(question, answer)
              }
            ){
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss
                }
            ) {
                Text("Cancel")
            }
        }


    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen(){
    WordDialogButton(
        title = "Word",
        label1 = "Question",
        label2 = "Answer",
        front = "car",
        back = "mobil",
        onConfirm = {front,back ->
            front
            back
        },
        onDismiss = {}
    )
}