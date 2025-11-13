package com.App.healtcare.ui.feature.settings.presentation._main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.model.UserInput
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val uiState: StateFlow<UserInput> = userRepository.getUserInput().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = UserInput(min = 0, max = 0)
    )
    fun saveSettings(min: Int, max: Int){
        viewModelScope.launch {
            userRepository.saveRangeSettings(min, max)
        }
    }
}