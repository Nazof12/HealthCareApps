package com.App.healtcare.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.App.healtcare.data.model.MathInputSet
import com.App.healtcare.data.model.UserInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
   private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys{
        val MIN_RANGE = intPreferencesKey("min_range")
        val MAX_RANGE = intPreferencesKey("max_range")
        val PLUS_OPR = booleanPreferencesKey("plus_opr")
        val DECREASE_OPR = booleanPreferencesKey("decrease_opr")
        val MULTIPLE_OPR = booleanPreferencesKey("multiple_opr")
        val DIVIDE_OPR = booleanPreferencesKey("divide_opr")
        val MANY_QUESTION = intPreferencesKey("many_question")
        val MANY_WORD = intPreferencesKey("many_word")
        //list of quiz
        val MATH_QUIZ = booleanPreferencesKey("math_quiz")
        val VOCABULARY_QUIZ = booleanPreferencesKey("vocabulary_quiz")
    }

    //this function will return one object to UserInput
    fun getUserInput(): Flow<UserInput>{
        return dataStore.data.map { preferences ->
            val min = preferences[PreferencesKeys.MIN_RANGE] ?: 1
            val max = preferences[PreferencesKeys.MAX_RANGE] ?: 10
            UserInput(
                min = min,
                max = max,

            )
        }

    }
    //option for new setting
    suspend fun saveRangeSettings(min: Int, max: Int){
        dataStore.edit { settings ->
            settings[PreferencesKeys.MIN_RANGE] = min
            settings[PreferencesKeys.MAX_RANGE] = max

        }
    }

    //start math settings
    //settings for math settings
    fun getMathSettings(): Flow<MathInputSet> {
        return dataStore.data.map { preferences ->
            val plus = preferences[PreferencesKeys.PLUS_OPR] ?: true
            val decrease = preferences[PreferencesKeys.DECREASE_OPR] ?: true
            val multiple = preferences[PreferencesKeys.MULTIPLE_OPR] ?: false
            val divide = preferences[PreferencesKeys.DIVIDE_OPR] ?: false
            MathInputSet(
                plus = plus,
                decrese = decrease,
                multiple = multiple,
                divide = divide
            )
        }
    }

    suspend fun saveMathSettings(plus: Boolean, decrease: Boolean,multiple: Boolean,divide: Boolean){
        dataStore.edit { settings ->
            settings[PreferencesKeys.PLUS_OPR] = plus
            settings[PreferencesKeys.DECREASE_OPR] = decrease
            settings[PreferencesKeys.MULTIPLE_OPR] = multiple
            settings[PreferencesKeys.DIVIDE_OPR] = divide
        }
    }
    //end of math settings

    //start of manyQuestion
    fun getManyQuestion(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.MANY_QUESTION] ?: 1
        }
    }
    suspend fun saveManyQuestion(many: Int){
        dataStore.edit { settings ->
            settings[PreferencesKeys.MANY_QUESTION] = many
        }
    }
    //end of manyQuestion

    //start of manyQuestion vocabulary
    fun getManyWord(): Flow<Int>{
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.MANY_WORD] ?: 1
        }
    }
    suspend fun saveManyWord(word: Int){
        dataStore.edit { settings ->
            settings[PreferencesKeys.MANY_WORD] = word
        }
    }
    //end of manyquestion vocabulary
    //start of list of quiz 

    fun getQuizType(): Flow<QuizType>{
        return dataStore.data.map { preferences ->
            val math = preferences[PreferencesKeys.MATH_QUIZ] ?: true
            val vocab = preferences[PreferencesKeys.VOCABULARY_QUIZ] ?: false
            QuizType(
                mathType = math,
                vocabType = vocab
            )
        }
    }

    suspend fun saveQuizType(math: Boolean, vocab: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MATH_QUIZ] = math
            preferences[PreferencesKeys.VOCABULARY_QUIZ] = vocab
        }
    }
    //end of list of quiz
}

//quiz type data Class
data class QuizType(
    val mathType: Boolean = true,
    val vocabType: Boolean = false
)