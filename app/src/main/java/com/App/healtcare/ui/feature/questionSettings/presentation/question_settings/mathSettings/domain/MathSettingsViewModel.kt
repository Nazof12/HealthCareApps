package com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.mathSettings.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.domain.model.MathInputSet
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MathSettingsViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel(){
    val switchState: StateFlow<MathInputSet> = userRepository.getMathSettings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = MathInputSet(
            plus = true,
            decrese = true,
            multiple = false,
            divide = false
        )
    )
    fun onToggleSwitch(plus: Boolean, decrease: Boolean, multiple: Boolean, divide: Boolean){
        viewModelScope.launch {
            userRepository.saveMathSettings(
                plus = plus,
                decrease = decrease,
                multiple = multiple,
                divide = divide
            )
        }
    }
}