package com.App.healtcare.data.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.App.healtcare.R
import com.App.healtcare.data.local.dao.AppInfoDao
import com.App.healtcare.data.local.entity.AppInfoEntity
import com.App.healtcare.data.model.AppItems
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val appInfoDao: AppInfoDao,
    @ApplicationContext private val context: Context
){
    private val packageManager: PackageManager = context.packageManager
    fun getAllTrackedApps(): Flow<List<AppInfoEntity>>{
        return appInfoDao.getAllAppInfo()
    }
    fun getLaunchableApps(): List<AppInfoEntity>{

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
                appName = appName,
            )
        }.sortedBy { it.appName.lowercase() }
    }
    fun getInstalledAppItems(): Flow<List<AppItems>>{
        return appInfoDao.getAllAppInfo()
            .map { entities ->
                withContext(Dispatchers.IO){
                    entities.map { entity ->
                        mapEntityToAppItem(entity)
                    }
                }
            }
    }

    private fun mapEntityToAppItem(entity: AppInfoEntity):AppItems{
        val icon: Drawable = try {
            packageManager.getApplicationIcon(entity.packageName)
        } catch (e: PackageManager.NameNotFoundException){
            context.getDrawable(R.drawable.ic_launcher_foreground)!!
        }
        return AppItems(
            packageName = entity.packageName,
            appName = entity.appName,
            isChecked = entity.isChecked,
            icon = icon
        )
    }
    suspend fun updateAppInfo(app: AppInfoEntity){
        appInfoDao.updateApp(app)
    }

    suspend fun getAppEntity(packageName: String): AppInfoEntity?{
        return appInfoDao.getAppByPackageName(packageName)
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