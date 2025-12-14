package com.App.healtcare.ui.feature.settings.presentation.question_settings.masterSettings.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.repository.QuizType
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){
    val masterState: StateFlow<Int> = userRepository.getManyQuestion().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = 1
    )
    fun saveSettings(many: Int) {
        viewModelScope.launch {
            userRepository.saveManyQuestion(many = many)
        }
    }

    val getQuizType: StateFlow<QuizType> = userRepository.getQuizType().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = QuizType()
    )
    fun saveQuizType(math: Boolean, vocab: Boolean){
        viewModelScope.launch {
            userRepository.saveQuizType(
                math = math,
                vocab = vocab
            )
        }
    }
}