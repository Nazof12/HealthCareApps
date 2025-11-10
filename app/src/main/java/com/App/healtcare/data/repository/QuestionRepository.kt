package com.App.healtcare.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.ui.quiz.DynamicQuestion
import com.App.healtcare.ui.quiz.QuestionGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
class QuestionRepository @Inject constructor(
    private val questionGenerator: QuestionGenerator
){

    fun getNewMathQuestion(min: Int, max: Int): DynamicQuestion{
        return questionGenerator.generateAddtionQuestion(min, max)

    }

}