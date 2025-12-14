package com.App.healtcare.ui.quiz.vocabulary.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import com.App.healtcare.data.repository.QuestionRepository
import com.App.healtcare.data.repository.UserRepository
import com.App.healtcare.ui.quiz.math.DynamicQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val questionRepository: QuestionRepository

) : ViewModel() {
    init {
        loadAllQuestion()
    }
    private val _uiState = MutableStateFlow(VocabUiState())
    val uiState: StateFlow<VocabUiState> = _uiState.asStateFlow()


    fun loadAllQuestion(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val allWords = questionRepository.getVocabularyWord().first()

            if(allWords.isNotEmpty()){
                val firstQuestion = mapEntityToQuestion(allWords[0])
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        vocabList = allWords,
                        currentQuestion = firstQuestion,
                        currentIndex = 0
                    )
                }
            } else{
                _uiState.update { it.copy(isLoading = false) }
            }

        }
    }

    fun submitAnswer(userAnswer: String){
        val currentQuestion = _uiState.value.currentQuestion ?: return
        val isCorrect = userAnswer == currentQuestion.answer
        if(isCorrect){
            _uiState.update { it.copy(isAnswerCorrect = true) }
            loadNextQuestion()

        }else{
            _uiState.update { it.copy(isAnswerCorrect = false) }
        }

    }
    private fun loadNextQuestion(){
        val currentState = _uiState.value
        val nextIndex = currentState.currentIndex + 1

        if (nextIndex < currentState.vocabList.size){
            val nextEntity = currentState.vocabList[nextIndex]
            val nextQuestion = mapEntityToQuestion(nextEntity)

            _uiState.update {
                it.copy(
                    currentIndex = nextIndex,
                    currentQuestion = nextQuestion,
                    isAnswerCorrect = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isAnswerCorrect = true
                )
            }
        }
    }
    private fun mapEntityToQuestion(entity: VocabularyEntity): DynamicVocabQuestion{
        return DynamicVocabQuestion(
            questionText = entity.question,
            answer = entity.answer
        )
    }
}
data class VocabUiState(
    val isLoading: Boolean = true,
    val currentQuestion: DynamicVocabQuestion? = null,
    val isAnswerCorrect: Boolean? = null,

    val currentIndex: Int = 0,
    val vocabList: List<VocabularyEntity> = emptyList()

)