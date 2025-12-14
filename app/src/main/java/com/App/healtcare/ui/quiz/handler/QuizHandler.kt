package com.App.healtcare.ui.quiz.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.ui.quiz.handler.domain.QuizHandlerViewModel
import com.App.healtcare.ui.quiz.math.QuizMathScreen
import com.App.healtcare.ui.quiz.math.QuizViewModel
import com.App.healtcare.ui.quiz.vocabulary.QuizVocabularyScreen
import com.App.healtcare.ui.quiz.vocabulary.domain.VocabularyViewModel
@Composable
fun QuizHandler(
    mathViewModel: QuizViewModel = hiltViewModel(),
    vocabViewModel : VocabularyViewModel = hiltViewModel(),
    quizHandlerViewModel: QuizHandlerViewModel = hiltViewModel(),
    onDismiss: () -> Unit
){

    val mathState by mathViewModel.uiState.collectAsState()
    val vocabState by vocabViewModel.uiState.collectAsStateWithLifecycle()
    val handlerState by quizHandlerViewModel.getTypeQuiz.collectAsStateWithLifecycle()
    // local state for marked the session vocab was done
    var isVocabSessionFinished by remember { mutableStateOf(false) }
    if(handlerState.vocabType && handlerState.mathType){
        if(!isVocabSessionFinished){
            QuizVocabularyScreen(
                state = vocabState,
                onSubmitAnswer = { answer ->
                    vocabViewModel.submitAnswer(answer)
                },
                onDismiss = {isVocabSessionFinished = true}
            )
        } else{
            QuizMathScreen(
                state = mathState,
                onSubmitAnswer = {answer ->
                    mathViewModel.submitAnswer(answer)
                },
                onDismiss = onDismiss
            )
        }

    } else if(handlerState.mathType){
        QuizMathScreen(
            state = mathState,
            onSubmitAnswer = {answer ->
                mathViewModel.submitAnswer(answer)
            },
            onDismiss = onDismiss
        )
    } else if(handlerState.vocabType){
        QuizVocabularyScreen(
            state = vocabState,
            onSubmitAnswer = { answer ->
                vocabViewModel.submitAnswer(answer)
            },
            onDismiss = onDismiss
        )

    } else{

    }



}