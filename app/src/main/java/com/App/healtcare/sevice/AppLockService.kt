package com.App.healtcare.sevice

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.App.healtcare.data.repository.AppRepository
import com.App.healtcare.ui.quiz.handler.QuizLockActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppLockService : AccessibilityService() {
   private val serviceJob =SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    @Inject
    lateinit var appRepository: AppRepository

    private var temporaryUnlockedPackage: String? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            val packageName = event.packageName?.toString() ?: return
            val isSystemInterference = packageName == "android" ||
                    packageName.contains("com.android.systemui") ||
                    packageName.contains("inputmethod") ||
                    packageName.contains("keyboard") ||
                    packageName.contains("launcher")
            if(temporaryUnlockedPackage != null &&
                packageName != temporaryUnlockedPackage &&
                packageName != this.packageName ){
                temporaryUnlockedPackage = null
                Log.d("AppLockService", "user keluar dari aplikasi, kunci diaktifkan kembali")
            }

            // don't lock self
            if(packageName == this.packageName || packageName.contains("launcher")) return

            if (packageName == temporaryUnlockedPackage) return

            serviceScope.launch(Dispatchers.IO) {
                val lockedApps = appRepository.getAllTrackedApps().first()
                    .filter { it.isChecked }
                    .map{ it.packageName }
                if(packageName in lockedApps){
                    val lockIntent = Intent(applicationContext, QuizLockActivity::class.java).apply{
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        putExtra("LOCKED_PACKAGE", packageName)
                    }
                    startActivity(lockIntent)
                }
            }

        }
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
    companion object{
        var instance: AppLockService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }
    fun unlockPackage(packageName: String){
        temporaryUnlockedPackage = packageName

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if(launchIntent != null){
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(launchIntent)
        }
    }

}
