package com.App.healtcare.data.repository

import android.content.Context
import android.content.Intent
import com.App.healtcare.data.local.dao.AppInfoDao
import com.App.healtcare.data.local.entity.AppInfoEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val appInfoDao: AppInfoDao,
    @ApplicationContext private val context: Context
){

    fun getAllTrackedApps(): Flow<List<AppInfoEntity>>{
        return appInfoDao.getAllAppInfo()
    }
    fun getLaunchableApps(): List<AppInfoEntity>{
        val packageManager = context.packageManager
        //create main Intent to search application in launcher
        val mainIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfoList = packageManager.queryIntentActivities(mainIntent,0)
        return resolveInfoList.mapNotNull{resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            val appName = resolveInfo.loadLabel(packageManager).toString()
            AppInfoEntity(
                packageName = packageName,
                appName = appName
            )
        }.sortedBy { it.appName.lowercase() }
    }
    suspend fun updateAppInfo(app: AppInfoEntity){
        appInfoDao.updateApp(app)
    }

    suspend fun syncAppsWithDatabase(){
        val deviceApps = getLaunchableApps()
        val dbApps = appInfoDao.getAllAppInfo().firstOrNull() ?: emptyList()

        deviceApps.forEach { deviceApps ->
            if(dbApps.none{it.packageName == deviceApps.packageName}){
                appInfoDao.insertAppInfo(deviceApps)
            }
        }
    }


}