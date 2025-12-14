package com.App.healtcare.ui.feature.settings.presentation.question_settings.vocabularySettings.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import com.App.healtcare.data.repository.QuestionRepository
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val vocabView : QuestionRepository,
    private val userRepository: UserRepository
): ViewModel() {
    val getWord : StateFlow<List<VocabularyEntity>> = vocabView.getVocabularyWord().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = emptyList()
    )

     fun insertWord(word: VocabularyEntity){
         viewModelScope.launch {
             vocabView.insertVocabularyWord(word)
         }

    }
     fun updateWord(word: VocabularyEntity){
         viewModelScope.launch {
             vocabView.updateVocabularyWord(word)
         }

    }
     fun deleteWord(ids: List<Int>){
        viewModelScope.launch {
            ids.forEach { id ->
                vocabView.deleteVocabularyWord(id)
            }

        }

    }
     fun saveWordById(word: Set<String>){
        viewModelScope.launch {
            userRepository.saveSelectedWordId(word)
        }
    }

    //start many word
    val getManyWord: StateFlow<Int> = userRepository.getManyWord().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = 1
    )
    fun saveManyWord(word: Int){
        viewModelScope.launch {
            userRepository.saveManyWord(word)
        }
    }
    //end many word
}