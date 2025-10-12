package com.App.healtcare.ui.home

import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.local.entity.AppInfoEntity
import com.App.healtcare.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.syncAppsWithDatabase()
        }
        observeAppsFromDb()
    }
    private fun observeAppsFromDb(){
        _uiState.update { it.copy(isLoading = true) }
        appRepository.getAllTrackedApps()
            .onEach { appFromDb ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        appList = appFromDb.sortedBy { app -> app.appName.lowercase() }
                    )
                }
            }
            .launchIn(viewModelScope)

    }
    fun onAppCheckedChange(app: AppInfoEntity, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val updateApp = app.copy(isChecked = isChecked)
            appRepository.updateAppInfo(updateApp)
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val appList: List<AppInfoEntity> = emptyList(),
    val errorMessage: String? = null
)