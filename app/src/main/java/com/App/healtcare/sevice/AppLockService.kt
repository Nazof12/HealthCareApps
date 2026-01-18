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
    private var lockedPackagesCache = setOf<String>()
    @Inject
    lateinit var appRepository: AppRepository

    private var temporaryUnlockedPackage: String? = null
    override fun onCreate(){
        super.onCreate()
        serviceScope.launch {
            appRepository.getAllTrackedApps().collect { apps ->
                lockedPackagesCache = apps.filter { it.isChecked }.map { it.packageName }.toSet()
            }
        }
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            val packageName = event.packageName?.toString() ?: return
            val isSystemInterference = packageName == "android" ||
                    packageName.contains("com.android.systemui") ||
                    packageName.contains("inputmethod") ||
                    packageName.contains("keyboard")
            if(isSystemInterference) return
            val isLauncher = packageName.contains("launcher") || packageName.contains("trebuchet")
            if(isLauncher || (temporaryUnlockedPackage != null && packageName != temporaryUnlockedPackage && packageName != this.packageName)) {
                stopService(Intent(this, TimerOverlayService::class.java))
                temporaryUnlockedPackage = null
                Log.d("AppLockService", "user keluar dari aplikasi, kunci diaktifkan kembali")
            }

            // don't lock self
            if(packageName == this.packageName || packageName.contains("launcher")) return
            if (packageName == temporaryUnlockedPackage) return

            if(lockedPackagesCache.contains(packageName)){
                Log.d("AppLockService", "MENANGKAP APLIKASI: $packageName")
                    val lockIntent = Intent(applicationContext, QuizLockActivity::class.java).apply{
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        putExtra("LOCKED_PACKAGE", packageName)
                    }
                    startActivity(lockIntent)
                }


        }
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        super.onDestroy()
        instance = null
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
        val timerIntent = Intent(this, TimerOverlayService::class.java)
        startService(timerIntent)
//        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
//        if(launchIntent != null){
//            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            startActivity(launchIntent)
//            startService(intent)
//        }
    }

}
