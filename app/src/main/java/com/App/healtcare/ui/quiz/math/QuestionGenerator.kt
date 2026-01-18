package com.App.healtcare.ui.quiz.math

import com.App.healtcare.data.domain.model.MathInputSet
import com.App.healtcare.data.repository.UserRepository
import javax.inject.Inject


data class DynamicQuestion(
    val questionText: String,
    val answer: Int
)
class QuestionGenerator @Inject constructor(
   private val userRepository: UserRepository
){


    fun generateAddtionQuestion(min: Int, max: Int, operator: MathInputSet): DynamicQuestion{

        val availableOperators = mutableListOf<Char>()
        if (operator.plus) availableOperators.add('+')
        if (operator.decrese) availableOperators.add('-')
        if (operator.multiple) availableOperators.add('*')
        if (operator.divide) availableOperators.add('/')

        if (availableOperators.isEmpty()) {
            throw IllegalStateException("No operator selected for the quiz.")
        }
        val randomOperator = availableOperators.random()

        var num1 = 0
        var num2 = 0
        do {
            val ranNum1 = (min..max).random()
            val ranNum2 = (min..max).random()

            num1 = ranNum1
            num2 = ranNum2
        }while (num2 >= num1)
        val result: Int = when(randomOperator){
            '+' -> num1 + num2
            '-' -> num1 - num2
            '/' -> {if (num2 != 0 && num1 % 2 == 0 && num2 % 2 == 0){
                num1 / num2
            }else{
                0 }
            }
            '*' -> num1 * num2
            else -> 0
        }
        val answerQuestion = result

        return DynamicQuestion(
            questionText = "$num1 $randomOperator $num2 ",
            answer = answerQuestion
        )
    }
}