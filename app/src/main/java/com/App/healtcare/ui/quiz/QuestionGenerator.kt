package com.App.healtcare.ui.quiz

import javax.inject.Inject
import kotlin.random.Random

data class DynamicQuestion(
    val questionText: String,
    val answer: Int
)
class QuestionGenerator @Inject constructor() {
    fun generateAddtionQuestion(min: Int, max: Int): DynamicQuestion{
        val num1 = Random.nextInt(min, max)
        val num2 = Random.nextInt(min, max)

        return DynamicQuestion(
            questionText = "$num1 + $num2 = ?",
            answer = num1 + num2,
        )
    }
}