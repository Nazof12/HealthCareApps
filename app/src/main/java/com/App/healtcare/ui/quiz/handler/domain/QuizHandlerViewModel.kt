package com.App.healtcare.ui.quiz.handler.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.repository.QuizType
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuizHandlerViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val getTypeQuiz : StateFlow<QuizType> = userRepository.getQuizType().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = QuizType()
    )

}