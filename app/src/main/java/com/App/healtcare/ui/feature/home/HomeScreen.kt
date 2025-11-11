package com.App.healtcare.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.ui.component.AppList
import com.App.healtcare.ui.component.ModernSearchBar


@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit
){

    val fileteredApps by viewmodel.filteredApps.collectAsStateWithLifecycle()
    val searchQuery by viewmodel.searchQuery.collectAsStateWithLifecycle()
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.statusBars.asPaddingValues())){
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        ModernSearchBar(
            value = searchQuery,
            onValueChange = {viewmodel.onSearchQueryChange(it)},
            hint = "Search"
        )
        AppList(
            apps = fileteredApps ,
            onAppCheckedChange = { app, isChecked ->
                viewmodel.onAppCheckedChange(app, isChecked)
            },
            modifier = Modifier.weight(1f),
            typeAppSwitch = false,
            iconModeApp = true,
        )
        }
        FeatureActivationButton(
            onClick = onNavigateToSettings,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        )
    }
}



@Composable
fun FeatureActivationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){

    IconButton(
        onClick = onClick,
        modifier = modifier

    ) {
        Icon(Icons.Default.Settings, contentDescription = "settings", modifier = Modifier.size(32.dp))
    }
}
