package com.App.healtcare.ui.quiz.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.ui.quiz.math.QuizViewModel
import com.App.healtcare.ui.quiz.vocabulary.domain.VocabularyViewModel
@Composable
fun QuizHandler(
    mathViewModel: QuizViewModel = hiltViewModel(),
    vocabViewModel : VocabularyViewModel = hiltViewModel()
){

    val mathState by mathViewModel.uiState.collectAsState()
    val vocabState by vocabViewModel.uiState.collectAsStateWithLifecycle()

}