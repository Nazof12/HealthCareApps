package com.App.healtcare

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.webkit.PermissionRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph
import com.App.healtcare.sevice.AppLockService
import com.App.healtcare.sevice.IsAccesibilityServiceEnabled
import com.App.healtcare.ui.component.PermissionGuard
import com.App.healtcare.ui.feature.home.HomeScreen
import com.App.healtcare.ui.navigation.AppNavGraph
import com.App.healtcare.ui.theme.HealtCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealtCareTheme {
                PermissionGuard{
                    AppNavGraph()
                }
            }

        }
    }

}


