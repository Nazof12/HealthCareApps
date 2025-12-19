package com.App.healtcare.ui.feature.extraSettings.main.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.repository.TimeSetup
import com.App.healtcare.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtraViewmodel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val getAppTimer : StateFlow<TimeSetup> = userRepository.getTime().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(200),
        initialValue = TimeSetup()
    )
    fun saveAppTime(timer: Int, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveTimeApp(
                time = timer,
                isChecked = isChecked
            )
        }
    }

}