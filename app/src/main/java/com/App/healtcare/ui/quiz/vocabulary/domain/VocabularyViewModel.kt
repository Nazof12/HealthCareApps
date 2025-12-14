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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(VocabUiState())
    val uiState: StateFlow<VocabUiState> = _uiState.asStateFlow()

    init {
        loadAllQuestion()
    }


    fun loadAllQuestion(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            try {

                val totalQuestion = userRepository.getManyQuestion().first()
                val idWord = userRepository.getSelectedWordId().first()
                    .mapNotNull { it.toIntOrNull() }
                    .toSet()
                val getQuizType = userRepository.getQuizType().first()
                val rawList = if (idWord.isEmpty()) {
                    questionRepository.getVocabularyWord().first()
                } else {
                    questionRepository.getWordById(idWord).first()
                }
                val allWord = rawList.shuffled()
                if (allWord.isNotEmpty()) {
                    val targetLimit = if (getQuizType.vocabType && getQuizType.mathType){
                        (totalQuestion/2).coerceAtLeast(1)
                    } else{
                        totalQuestion
                    }
                    val finalLimit = min(targetLimit, allWord.size)
                    if(finalLimit > 0){
                        val firstQuestion = mapEntityToQuestion(allWord[0])
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                vocabList = allWord,
                                currentQuestion = firstQuestion,
                                currentIndex = 0,
                                totalStep = finalLimit,
                                currentStep = 1
                            )
                        }
                    }
                    else{
                        _uiState.update { it.copy(isLoading = false) }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }catch (e: Exception){
                e.printStackTrace()
                println("ERROR CRACH: $e")
                _uiState.update { it.copy(isLoading = false) }
            }

        }
    }

    fun submitAnswer(userAnswer: String){
        val currentQuestion = _uiState.value.currentQuestion ?: return

        val isCorrect = userAnswer.trim().equals(currentQuestion.answer.trim(), ignoreCase = true)
        if(isCorrect){
            val currentStep = _uiState.value.currentStep
            val totalStep = _uiState.value.totalStep
            if(currentStep >= totalStep){
                _uiState.update {
                    it.copy(
                        isAnswerCorrect = true,
                        isQuizFinished = true,
                        isLoading = false
                    )
                }
            } else{
                _uiState.update {
                    it.copy(
                        currentStep = currentStep +1,
                        isAnswerCorrect = null,
                        isLoading = true
                    )
                }
                loadNextQuestion()
            }

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
                    isAnswerCorrect = null,
                    isLoading = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isAnswerCorrect = true,
                    isLoading = false
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
    val vocabList: List<VocabularyEntity> = emptyList(),

    val currentStep: Int = 1,
    val totalStep: Int = 1,
    val isQuizFinished: Boolean = false

)