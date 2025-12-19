package com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.mathSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.App.healtcare.data.model.MathInputSet
import com.App.healtcare.data.model.UserInput
import com.App.healtcare.ui.feature.questionSettings.component.ButtonMathSettings
import com.App.healtcare.ui.feature.questionSettings.component.ButtonSettings
import com.App.healtcare.ui.feature.questionSettings.component.ValueInputDialog
import com.App.healtcare.ui.feature.questionSettings.presentation._main.SettingsViewModel
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.mathSettings.domain.MathSettingsViewModel
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink

@Composable
fun MathSettings(
    viewModel: SettingsViewModel = hiltViewModel(),
    mathSetView: MathSettingsViewModel = hiltViewModel(),
    navController: NavController
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val switchState by mathSetView.switchState.collectAsStateWithLifecycle()
    MathSettingContent(
        uiState = uiState,
        onSaveClicked = viewModel::saveSettings,
        navController = navController,
        mathSetView = switchState,
        mathSaveSwitch = mathSetView::onToggleSwitch
    )



}

@Composable
fun MathSettingContent(
    uiState: UserInput,
    onSaveClicked: (min: Int, max: Int) -> Unit,
    mathSetView: MathInputSet,
    mathSaveSwitch: (plus: Boolean, decrease: Boolean,
            multiple: Boolean, divide: Boolean) -> Unit,
    navController: NavController
){
    //start range input number
    var minInput by remember(uiState.min) {mutableStateOf(uiState.min.toString())}
    var maxInput by remember(uiState.max) {mutableStateOf(uiState.max.toString())}
    var showMinInputDialog by remember { mutableStateOf(false)}
    var showMaxInputDialog by remember { mutableStateOf(false)}
    //end range input number

    //start operator input
    val mathset = mathSetView
    //end operator input
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
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear button",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .offset(-10.dp)
                        .clickable(
                            enabled = true,
                            onClick ={
                                navController.navigateUp()
                            }
                        )
                )
                Text(
                    text = "Math Config",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "check button",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            val min = minInput.toIntOrNull() ?: 0
                            val max = maxInput.toIntOrNull() ?: 0
                            onSaveClicked(min, max)
                            navController.navigateUp()
                        }
                    )
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 125.dp)

    ) {
        Text(
            text = "Range Settings",
            color = GrayText,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.padding(top = 28.dp))
        ButtonMathSettings(
            textHeader = "Minimum Number",
            textSemi = minInput,
            onClick = {
                showMinInputDialog = true
            }
        )
        if(showMinInputDialog){
            ValueInputDialog(
                title = "Minimum Number",
                initialValue = minInput,
                onDismissRequest = {showMinInputDialog = false },
                onConfirm = {newValue ->
                    minInput = newValue
                    showMinInputDialog = false
                }
            )
        }
        ButtonMathSettings(
            textHeader = "Maximum Number",
            textSemi = maxInput,
            onClick = {
                showMaxInputDialog = true
            }
        )
        if(showMaxInputDialog){
            ValueInputDialog(
                title = "Maximum Number",
                initialValue = maxInput,
                onDismissRequest = {
                    showMaxInputDialog = false
                },
                onConfirm = {newValue ->
                    maxInput = newValue
                    showMaxInputDialog = false
                }
            )
        }
        // start operator settings
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Operator Settings",
            color = GrayText,
            fontSize = 18.sp
        )
        ButtonSettings(
            text = "Plus",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = mathset.plus ,
            onCheckedChange = { newPlus ->
                mathSaveSwitch(
                   newPlus,
                    mathset.decrese,
                    mathset.divide,
                    mathset.multiple
                )

            }
        )
        ButtonSettings(
            text = "Decrease",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = mathset.decrese ,
            onCheckedChange = { newDecrease ->
                mathSaveSwitch(
                    mathset.plus,
                    newDecrease,
                    mathset.divide,
                    mathset.multiple
                )

            }
        )
        ButtonSettings(
            text = "Multiple",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = mathset.multiple ,
            onCheckedChange = { newMultiple ->
                mathSaveSwitch(
                    mathset.plus,
                    mathset.decrese,
                    newMultiple,
                    mathset.divide
                )

            }
        )
        ButtonSettings(
            text = "Divide",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = mathset.divide ,
            onCheckedChange = { newDivide ->
                mathSaveSwitch(
                    mathset.plus,
                    mathset.decrese,
                    mathset.multiple,
                    newDivide
                )

            }
        )
        //end operator Settings
//        OutlinedTextField(
//            value = minInput,
//            onValueChange = {minInput = it},
//            label = {Text("min value")}
//        )
//
//        OutlinedTextField(
//            value = maxInput,
//            onValueChange = {maxInput = it},
//            label = {Text("max value")}
//        )

//        Button(
//            onClick = {
//                val min = minInput.toIntOrNull() ?: 0
//                val max = maxInput.toIntOrNull() ?: 0
//                onSaveClicked(min, max)
//            }
//        ){
//            Text("save")
//        }
    }

}

@Preview(showBackground = true)
@Composable
fun MathSettingsContentPreview() {
    // 1. Buat Mock State
    val mockUiState = UserInput(
        min = 10,
        max = 100
    )
    val mockNav = rememberNavController()
    val mockSwitch = MathInputSet(
        plus = true,
        decrese = true,
        multiple = false,
        divide = false
    )

    // 2. Panggil Composable Stateless dengan mock data
    // Pastikan kamu membungkusnya dengan tema aplikasimu
    // Theme.YourAppNameTheme {
    MathSettingContent(
        uiState = mockUiState,
        onSaveClicked = { min, max ->
            println("Simulasi: Menyimpan min=$min, max=$max")
        },
        navController = mockNav,
        mathSetView = mockSwitch,
        mathSaveSwitch = {plus, decrese, multiple, divide ->
            println("simulasi $plus")
        },
    )
    // }
}