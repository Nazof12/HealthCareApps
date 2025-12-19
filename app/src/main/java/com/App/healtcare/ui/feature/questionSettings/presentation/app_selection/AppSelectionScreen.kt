package com.App.healtcare.ui.feature.questionSettings.presentation.app_selection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.ui.component.AppList
import com.App.healtcare.ui.component.ModernSearchBar
import com.App.healtcare.ui.feature.home.HomeViewModel

@Composable
fun AppCheckedScreen(
    viewModel: HomeViewModel = hiltViewModel()
){
    val filteredApps by viewModel.filteredApps.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ){
        Column {
            ModernSearchBar(
                value = searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryChange(it)
                },
                hint = "Search"
            )
            AppList(
                apps = filteredApps,
                onAppCheckedChange = {app, isChecked ->
                    viewModel.onAppCheckedChange(app, isChecked)
                },
                typeAppSwitch = true,
                iconModeApp = false,
                onClickApp = {}
            )
        }

    }
}