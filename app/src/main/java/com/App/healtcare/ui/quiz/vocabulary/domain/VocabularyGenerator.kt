package com.App.healtcare.ui.quiz.vocabulary.domain

import androidx.lifecycle.ViewModel
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton


class VocabularyGenerator @Inject constructor(

){
    fun generateVocabQuestion( dbVocab: VocabularyEntity) : DynamicVocabQuestion{
        val answerDb = dbVocab.answer.lowercase()
        val questionDb = dbVocab.question.lowercase()

        return DynamicVocabQuestion(
            questionText = "What is $questionDb?",
            answer = answerDb
        )
    }
}

data class DynamicVocabQuestion(
    val questionText: String,
    val answer: String
)