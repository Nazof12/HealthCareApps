package com.App.healtcare.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.data.local.entity.AppInfoEntity


@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = hiltViewModel()
){
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.statusBars.asPaddingValues())){
    Row(
    ){
        AppList(
            apps = uiState.appList,
            onAppCheckedChange = {app, isChecked ->
                viewmodel.onAppCheckedChange(app, isChecked)
            },
            modifier = Modifier.weight(1f)
            )
        }
        FeatureActivationButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
fun AppList(
    apps: List<AppInfoEntity>,
    onAppCheckedChange: (AppInfoEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier
){
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = apps,
                key = {app -> app.packageName}
            ){ app ->
                AppItem(
                    app = app,
                    onCheckedChange = { isChecked ->
                        onAppCheckedChange(app, isChecked)
                    }
                )
            }
        }
}

@Composable
fun AppItem(
    app: AppInfoEntity,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable{onCheckedChange(!app.isChecked)}
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
            Text(
                text = app.appName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
//            Text(
//                text = app.packageName,
//                style = MaterialTheme.typography.bodySmall,
//                overflow = TextOverflow.Ellipsis
//            )
        Switch(
            checked = app.isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.8f)

        )
    }
}
fun openAccessibilitySettings(context: Context){
    val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivity(intent)
}
@Composable
fun FeatureActivationButton(
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    IconButton(
        onClick = {openAccessibilitySettings(context)},
        modifier = modifier

    ) {
        Icon(Icons.Default.Settings, contentDescription = "settings", modifier = Modifier.scale(1.2f))
    }
}
@Preview(showBackground = true)
@Composable
fun AppItemPreview(){
    val sampleApp = AppInfoEntity(packageName = "com.example.app", appName = "my awsome app")
    AppItem(app = sampleApp, onCheckedChange = {})
}