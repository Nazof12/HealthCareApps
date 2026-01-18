package com.App.healtcare.data.domain.model

import android.graphics.drawable.Drawable

data class AppItems(
    val packageName: String,
    val appName: String,
    val isChecked: Boolean = false,
    val icon: Drawable
)
