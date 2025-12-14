package com.App.healtcare.ui.quiz.math

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.repository.QuestionRepository
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject() constructor(
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository
) : ViewModel(){
    init {
        initializeQuiz()
}
    // this state will be shown on UI
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private fun initializeQuiz(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val totalQuestion = try {
                userRepository.getManyQuestion().first()
            }catch (e: Exception){
                1
            }

            _uiState.update {
                it.copy(
                    totalStep = totalQuestion,
                    currentStep = 1,
                    isQuizFinished = false
                )
            }
            loadNewQuestion()
        }
    }
    fun loadNewQuestion(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, isAnswerCorrect = null)}
           try {

            val userInput = try {
                userRepository.getUserInput().first()
            }  catch (e: Exception){
                throw e
            }
            val operators = try {
                userRepository.getMathSettings().first()
            }   catch (e: Exception){
                throw e
            }
               val newQuestion = questionRepository.getNewMathQuestion(
                   min = userInput.min,
                   max = userInput.max,
                   operators = operators
               )
               _uiState.update {
                   it.copy(
                       currentQuestion = newQuestion,
                       isLoading = false
                   )
               }
           }catch (e: Exception){
               e.printStackTrace()
               _uiState.update { it.copy(isLoading = false) }
           }



        }
    }

    fun submitAnswer(userAnswer: String){
        val answerInt = userAnswer.toIntOrNull() ?: return
        val currentQuestion = _uiState.value.currentQuestion ?: return
        val isCorrect = answerInt == currentQuestion.answer
        if(isCorrect){
            val currentStep = _uiState.value.currentStep
            val totalStep = _uiState.value.totalStep
            if(currentStep >= totalStep){
                _uiState.update {
                    it.copy(
                        isAnswerCorrect = true,
                        isQuizFinished = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        currentStep = currentStep +1,
                        isAnswerCorrect = null,
                        isLoading = true
                    )
                }
                loadNewQuestion()
            }
        } else {
            _uiState.update { it.copy(isAnswerCorrect = false) }
        }
    }
}

data class QuizUiState(
    val isLoading: Boolean = true,
    val currentQuestion: DynamicQuestion? = null,
    val isAnswerCorrect: Boolean? = null,

    val currentStep: Int = 1,
    val totalStep: Int = 1,
    val isQuizFinished: Boolean = false
)