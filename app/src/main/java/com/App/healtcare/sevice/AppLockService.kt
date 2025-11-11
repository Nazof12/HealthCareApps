package com.App.healtcare.sevice

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.App.healtcare.data.repository.AppRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppLockService : AccessibilityService(){
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    @Inject
    lateinit var appRepository: AppRepository

    private var currentLockedPackage: String? = null


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            val packageName = event.packageName?.toString() ?: return

            if(packageName == currentLockedPackage){
                currentLockedPackage = null
                return
            }
            serviceScope.launch(Dispatchers.IO) {
                val lockedApps = appRepository.getAllTrackedApps().first()
                    .filter { it.isChecked }
                    .map{it.packageName}

                if(packageName in lockedApps){
                    currentLockedPackage = packageName
                    goHome()
                }
            }

        }
    }
    private fun goHome(){
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
