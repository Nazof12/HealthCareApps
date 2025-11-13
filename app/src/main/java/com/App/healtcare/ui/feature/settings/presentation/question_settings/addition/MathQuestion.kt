package com.App.healtcare.ui.feature.settings.presentation.question_settings.addition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.ui.feature.settings.presentation._main.SettingsViewModel

@Composable
fun MathSettings(
    viewModel: SettingsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var minInput by remember(uiState.min) { mutableStateOf(uiState.min.toString()) }
    var maxInput by remember(uiState.max) {mutableStateOf(uiState.max.toString())}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Text("Range Settings")
        OutlinedTextField(
            value = minInput,
            onValueChange = {minInput = it},
            label = {Text("min value")}
        )

        OutlinedTextField(
            value = maxInput,
            onValueChange = {maxInput = it},
            label = {Text("max value")}
        )

        Button(
          onClick = {
              val min = minInput.toIntOrNull() ?: 0
              val max = maxInput.toIntOrNull() ?: 0
              viewModel.saveSettings(min, max)
          }
        ){
            Text("save")
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun Preview(){
//    val fakeViewModel = object : SettingsViewModel() {
//        override val uiState = MutableStateFlow(UserInput(min = 1, max = 10))
//
//        override fun saveSettings(min: Int, max: Int) {
//            println("Preview: Tombol Simpan diklik dengan min=$min, max=$max")
//        }
//    }
//
//    MathSettings(
//        viewModel = fakeViewModel
//    )
//
//}