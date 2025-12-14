package com.App.healtcare.ui.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.App.healtcare.sevice.AppLockService
import com.App.healtcare.sevice.IsAccesibilityServiceEnabled
import com.App.healtcare.sevice.IsOverlayPermissionGranted


@Composable
fun PermissionGuard(
    content: @Composable () -> Unit
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isAccessibilityEnabled by remember {
        mutableStateOf(IsAccesibilityServiceEnabled(context, AppLockService::class.java))
    }

    var isOverlayEnabled by remember {
        mutableStateOf(IsOverlayPermissionGranted(context))
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver {_, event ->
            if (event == Lifecycle.Event.ON_RESUME){
                isAccessibilityEnabled = IsAccesibilityServiceEnabled(context, AppLockService::class.java)
                isOverlayEnabled = IsOverlayPermissionGranted(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    if (!isAccessibilityEnabled) {
        // Tahap 1: Minta Accessibility
        PermissionDialog(
            title = "Izin Accessibility Diperlukan",
            description = "Aplikasi ini membutuhkan Accessibility Service agar fitur App Lock dapat berjalan. Mohon aktifkan 'HealthCare' di pengaturan.",
            buttonText = "Buka Pengaturan Accessibility",
            onConfirm = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                context.startActivity(intent)
            }
        )
    } else if (!isOverlayEnabled) {
        // Tahap 2: Minta Overlay
        PermissionDialog(
            title = "Izin Overlay Diperlukan",
            description = "Aplikasi ini perlu izin 'Display over other apps' agar layar kuis bisa muncul di atas aplikasi lain.",
            buttonText = "Izinkan Overlay",
            onConfirm = {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${context.packageName}")
                )
                context.startActivity(intent)
            }
        )
    } else {
        // Tahap 3: Semua Izin OK -> Tampilkan Konten Utama
        content()
    }
}
@Composable
private fun PermissionDialog(
    title: String,
    description: String,
    buttonText: String,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {}, // Tidak bisa ditutup paksa
        title = { Text(text = title) },
        text = { Text(text = description) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = buttonText)
            }
        }
    )
}