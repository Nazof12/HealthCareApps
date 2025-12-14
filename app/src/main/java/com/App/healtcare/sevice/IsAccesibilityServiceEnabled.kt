package com.App.healtcare.sevice

import android.content.Context
import android.provider.Settings
import android.text.TextUtils

fun IsAccesibilityServiceEnabled(context: Context,serviceClass: Class<*>): Boolean{
    val expectedComponentName = "${context.packageName}/${serviceClass.canonicalName}"
    val enabledServiceSetting = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    val colonSplitter = TextUtils.SimpleStringSplitter(':')
    colonSplitter.setString(enabledServiceSetting)

    while(colonSplitter.hasNext()){
        val componentName = colonSplitter.next()
        if(componentName.equals(expectedComponentName, ignoreCase = true)){
            return true
        }
    }
    return false
}

fun IsOverlayPermissionGranted(context: Context): Boolean{
    return Settings.canDrawOverlays(context)
}