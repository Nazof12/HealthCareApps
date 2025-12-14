package com.App.healtcare.ui.feature.settings.presentation.question_settings.vocabularySettings.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import com.App.healtcare.ui.feature.settings.component.ButtonMathSettings
import com.App.healtcare.ui.feature.settings.component.ButtonSettings
import com.App.healtcare.ui.feature.settings.component.ValueInputDialog
import com.App.healtcare.ui.feature.settings.component.WordDialogButton
import com.App.healtcare.ui.feature.settings.presentation.question_settings.vocabularySettings.domain.VocabularyViewModel
import com.App.healtcare.ui.theme.GrayText
import com.App.healtcare.ui.theme.MyPink

@Composable
fun VocabularyWord(
    wordView: VocabularyViewModel = hiltViewModel(),
    navController: NavController
){
    val uiState by wordView.getWord.collectAsStateWithLifecycle()
        val uiManyWord by wordView.getManyWord.collectAsStateWithLifecycle()
        VocabularyContent(
            uiState = uiState,
            uiManyWord = uiManyWord,
            onSaveButton = wordView::insertWord,
            onSaveButtonManyWord = wordView::saveManyWord,
            navController = navController,
            onDeleteButton = wordView::deleteWord,
            onUpdateButton = wordView::updateWord
        )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VocabularyContent(
    uiState: List<VocabularyEntity>,
    uiManyWord: Int,
    onSaveButtonManyWord: (Int) -> Unit,
    onSaveButton: (word: VocabularyEntity) -> Unit,
    onDeleteButton: (List<Int>) -> Unit,
    onUpdateButton: (VocabularyEntity) -> Unit,
    navController: NavController
) {

    var isSelectionMode by remember { mutableStateOf(false) }
    //save ID items who is selected
    var selectedItems by remember { mutableStateOf(setOf<Int>()) }

    // save item who wanna be edited
    var itemToEdit by remember { mutableStateOf<VocabularyEntity?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 11.dp,
                    bottomEnd = 11.dp
                )
            )
            .background(if (isSelectionMode) Color.DarkGray else MyPink)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 27.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(!isSelectionMode) {
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
                                navController.navigateUp()
                            }
                        )
                )
            }
            if (isSelectionMode) {



                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "cancel selection",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            isSelectionMode = false
                            selectedItems = emptySet()
                        }
                )
                Text(
                    text = "${selectedItems.size} Selected",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Box {
                    var showMenu by remember { mutableStateOf(false) }
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { showMenu = true }
                    )
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        if (selectedItems.size >= 1) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = {
                                    val idToEdit = selectedItems.first()
                                    itemToEdit = uiState.find { it.id == idToEdit }
                                    showEditDialog = true
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = {Text("Set as Many Word")},
                                onClick = {

                                }
                            )
                            DropdownMenuItem(
                                text = {Text("Delete")},
                                onClick = {
                                    onDeleteButton(selectedItems.toList())
                                    showMenu = false
                                    isSelectionMode = false
                                    selectedItems = emptySet()
                                }
                            )
                        }
                    }
                }


            }

        }
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 125.dp)
        ) {
            Text(
                text = "Word Settings",
                color = GrayText,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.padding(top = 28.dp))
            var showAddWordDialog by remember { mutableStateOf(false) }

            var frontHandle by remember { mutableStateOf("") }
            var backHandle by remember { mutableStateOf("") }
            ButtonSettings(
                text = "Add Word",
                onClick = {
                    showAddWordDialog = true
                },
                arrowButton = true,
                switchButton = false,
                isChecked = false,
                onCheckedChange = {}
            )

            if(showEditDialog && itemToEdit != null){
                WordDialogButton(
                    title = "Edit Word",
                    label1 = "New Question",
                    label2 =" New Answer",
                    front = itemToEdit!!.question,
                    back = itemToEdit!!.answer,
                    onConfirm ={ newQ, newA ->
                        onUpdateButton(itemToEdit!!.copy(question = newQ, answer = newA))
                        showEditDialog = false
                        isSelectionMode = false
                        selectedItems = emptySet()
                    },
                    onDismiss = {showEditDialog = false},
                )

            }
            if (showAddWordDialog) {
                WordDialogButton(
                    title = "Add Word",
                    label1 = "Word Question",
                    label2 = "Word Answer",
                    front = frontHandle,
                    back = backHandle,
                    onConfirm = { front, back ->
                        onSaveButton(
                            VocabularyEntity(question = front, answer = back)
                        )
                        showAddWordDialog = false
                    },
                    onDismiss = {
                        showAddWordDialog = false
                    }
                )
            }
            var showManyWordDialog by remember {mutableStateOf(false)}
            var numberManyWord by remember(uiManyWord) { mutableStateOf(uiManyWord.toString()) }
            Spacer(modifier = Modifier.height(28.dp))
            ButtonMathSettings(
                textHeader = "Many Word",
                textSemi = numberManyWord,
                onClick ={
                    showManyWordDialog = true
                }
            )
            if(showManyWordDialog){
                ValueInputDialog(
                    title = "Many word will be used",
                    initialValue = numberManyWord,
                    onDismissRequest = {
                        showManyWordDialog = false
                    },
                    onConfirm = {newValue ->
                        val many = newValue
                        onSaveButtonManyWord(many.toIntOrNull() ?: 1)
                        showManyWordDialog = false
                    }
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Question",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp), // 50% Lebar
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Answer",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp), // 50% Lebar
                    fontWeight = FontWeight.Bold
                )
            }
            LazyColumn {
                items(uiState) { item ->

                    val isSelected = selectedItems.contains(item.id)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(if (isSelected) Color.LightGray.copy(alpha = 0.5f) else Color.Transparent)
                            .combinedClickable(
                                onClick = {
                                    if (isSelectionMode) {
                                        val newSelection = selectedItems.toMutableSet()
                                        if (isSelected) newSelection.remove(item.id) else newSelection.add(
                                            item.id
                                        )
                                        selectedItems = newSelection

                                        if (newSelection.isEmpty()) isSelectionMode = false
                                    }
                                },
                                onLongClick = {
                                    if (!isSelectionMode) {
                                        isSelectionMode = true
                                        selectedItems = setOf(item.id)
                                    }
                                }
                            )
                    ) {
                        if(isSelectionMode){
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {checked ->
                                    val newSelection = selectedItems.toMutableSet()
                                    if(checked) newSelection.add(item.id) else newSelection.remove(item.id)
                                    selectedItems = newSelection
                                    if(newSelection.isEmpty()) isSelectionMode = false
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Text(
                            text = item.question,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                        VerticalDivider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier
                                .fillMaxHeight()
                        )
                        Text(
                            text = item.answer,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                }
            }


        }

}

@Preview(showBackground = true)
@Composable
fun Previews(){
    val uiStateMock: List<VocabularyEntity> = emptyList()
    val uiManyWord: Int = 1
    VocabularyContent(
        uiState = uiStateMock,
        onSaveButton = {
        },
        navController = rememberNavController(),
        onDeleteButton = {},
        onUpdateButton = {},
        uiManyWord = uiManyWord,
        onSaveButtonManyWord = {}
    )
}