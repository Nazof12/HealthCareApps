package com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.masterSettings.representation

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
import com.App.healtcare.data.repository.QuizType
import com.App.healtcare.ui.feature.questionSettings.component.ButtonMathSettings
import com.App.healtcare.ui.feature.questionSettings.component.ButtonSettings
import com.App.healtcare.ui.feature.questionSettings.component.ValueInputDialog
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.masterSettings.domain.MasterViewModel
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink



@Composable
fun MasterSettings(
    masterView: MasterViewModel = hiltViewModel(),
    navController: NavController
){
    val uiState by masterView.masterState.collectAsStateWithLifecycle()
    val uiQuizType by masterView.getQuizType.collectAsStateWithLifecycle()
    MasterSettingsContent(
        uiState = uiState,
        uiQuizType = uiQuizType,
        onSaveClicked = masterView::saveSettings,
        onSaveQuizType = masterView::saveQuizType,
        navController = navController
    )
}
@Composable
fun MasterSettingsContent(
    uiState: Int,
    uiQuizType: QuizType,
    onSaveClicked: (many: Int) -> Unit,
    onSaveQuizType: (math: Boolean, vocab: Boolean) -> Unit,
    navController: NavController
){
    var showManyQuestion by remember { mutableStateOf(false) }
    var manyQuestion by remember(uiState) { mutableStateOf(uiState.toString()) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp)
            .clip(RoundedCornerShape(
                bottomStart = 11.dp,
                bottomEnd = 11.dp
            ))
            .background(MyPink)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
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
                            onClick = {
                                navController.navigateUp()
                            }
                        )
                )
                Text(
                    text = "Master Config",
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
                            val many = manyQuestion.toIntOrNull() ?: 0
                            onSaveClicked(many)
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
            text = "Question",
            color = GrayText,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.padding(top = 28.dp))


        ButtonMathSettings(
            textHeader = "Many Question",
            textSemi = manyQuestion,
            onClick = {
                showManyQuestion = true
            }
        )
        if(showManyQuestion){
            ValueInputDialog(
                title = "Many Question",
                initialValue = manyQuestion,
                onDismissRequest = {showManyQuestion = false },
                onConfirm = {newValue ->
                    manyQuestion = newValue
                    showManyQuestion = false
                }
            )
        }
        var mathType by remember { mutableStateOf(true) }
        var vocabType by remember { mutableStateOf(false)}
        ButtonSettings(
            text = "Math Question",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = uiQuizType.mathType,
            onCheckedChange = {it ->
                mathType = it
                onSaveQuizType(mathType, uiQuizType.vocabType)

            }
        )
        ButtonSettings(
            text = "Vocabulary Question",
            onClick = {},
            arrowButton = false,
            switchButton = true,
            isChecked = uiQuizType.vocabType,
            onCheckedChange = {it ->
                vocabType = it
                onSaveQuizType(uiQuizType.mathType, vocabType)
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun previews(){
    val mockUiState = 5
    val mockQuiztype = QuizType()
    MasterSettingsContent(
        uiState = mockUiState,
        onSaveClicked = {},
        navController = rememberNavController(),
        uiQuizType = mockQuiztype,
        onSaveQuizType = {vocab, math ->

        },
    )
}