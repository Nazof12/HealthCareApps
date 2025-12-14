package com.App.healtcare.ui.feature.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val fileteredApps by viewmodel.filteredApps.collectAsStateWithLifecycle()
    val searchQuery by viewmodel.searchQuery.collectAsStateWithLifecycle()
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.statusBars.asPaddingValues())
        .background(color = Color.White)
    ){
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
            onClickApp = {packageName ->
                val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                if(intent != null){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Gagal membuka aplikasi", Toast.LENGTH_SHORT).show()
                }
            }
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
