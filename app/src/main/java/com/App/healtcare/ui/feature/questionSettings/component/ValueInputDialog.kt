package com.App.healtcare.ui.feature.questionSettings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ValueInputDialog(
    title: String,
    initialValue: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
){

    var text by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title)
        },
        text = {
            Column {
                Text("Enter a new value:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = {text = it},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(text)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun PreViews(){
    ValueInputDialog(
        title = "Min Number",
        initialValue = "2",
        onDismissRequest = {} ,
        onConfirm = {}
    )
}




