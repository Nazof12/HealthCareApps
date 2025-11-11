package com.App.healtcare.ui.feature.home

import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.local.entity.AppInfoEntity
import com.App.healtcare.data.model.AppItems
import com.App.healtcare.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

//    private val _uiState = MutableStateFlow(HomeUiState())
//    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
//
//    init{
//        viewModelScope.launch(Dispatchers.IO) {
//            appRepository.syncAppsWithDatabase()
//        }
//        observeAppsFromDb()
//    }
//    private fun observeAppsFromDb(){
//        _uiState.update { it.copy(isLoading = true) }
//        appRepository.getInstalledAppItems()
//            .onEach { appFromDb ->
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        appList = appFromDb.sortedBy { app -> app.appName.lowercase() }
//                    )
//                }
//            }
//            .launchIn(viewModelScope)
//
//    }
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredApps: StateFlow<List<AppItems>> =
        _searchQuery.combine(appRepository.getInstalledAppItems()){query, apps ->
            if(query.isBlank()){
                apps
            } else {
                apps.filter {
                    it.appName.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun onSearchQueryChange(query: String){
        _searchQuery.value = query
    }
    fun onAppCheckedChange(app: AppItems, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val appEntity = appRepository.getAppEntity(app.packageName) ?: return@launch
            appRepository.updateAppInfo(appEntity.copy(isChecked = isChecked))
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
//    val appList: List<AppItems> = emptyList(),
    val searchQuery: String = ""
)