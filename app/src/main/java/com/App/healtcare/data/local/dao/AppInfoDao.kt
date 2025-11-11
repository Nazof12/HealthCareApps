package com.App.healtcare.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.App.healtcare.data.local.entity.AppInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao {
    @Query("SELECT * FROM app_info")
     fun getAllAppInfo(): Flow<List<AppInfoEntity>>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAppInfo(appInfo : AppInfoEntity)

     @Query("SELECT * FROM app_info WHERE packageName = :packageName LIMIT 1")
     suspend fun getAppByPackageName(packageName: String): AppInfoEntity?
     @Update
     suspend fun updateApp(isChecked : AppInfoEntity)

}