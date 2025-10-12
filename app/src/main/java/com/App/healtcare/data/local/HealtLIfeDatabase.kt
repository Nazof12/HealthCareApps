package com.App.healtcare.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.App.healtcare.data.local.dao.AppInfoDao
import com.App.healtcare.data.local.entity.AppInfoEntity

@Database(entities = [AppInfoEntity::class], version = 1, exportSchema = false)
abstract class HealtLIfeDatabase : RoomDatabase(){
    abstract fun appInfoDao(): AppInfoDao

}