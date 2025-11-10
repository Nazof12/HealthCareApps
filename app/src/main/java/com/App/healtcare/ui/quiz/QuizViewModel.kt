package com.App.healtcare.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.repository.QuestionRepository
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject() constructor(
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository
) : ViewModel(){ init {

}
    // this state will be shown on UI
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun loadNewQuestion(){
        viewModelScope.launch(Dispatchers.IO) {
           val userInput = userRepository.getUserInput().first()
            val newQuestion = questionRepository.getNewMathQuestion(
                min = userInput.min,
                max = userInput.max
            )
            _uiState.update { it.copy(currentQuestion = newQuestion) }
        }
    }

    fun submitAnswer(userAnswer: String){
        val answerInt = userAnswer.toIntOrNull() ?: return
        val currentQuestion = _uiState.value.currentQuestion ?: return

        val isCorrect = answerInt == currentQuestion.answer
        _uiState.update { it.copy(isAnswerCorrect = isCorrect) }
    }
}

data class QuizUiState(
    val isLoading: Boolean = true,
    val currentQuestion: DynamicQuestion? = null,
    val isAnswerCorrect: Boolean? = null
)