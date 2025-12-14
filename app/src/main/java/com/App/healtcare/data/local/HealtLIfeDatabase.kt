package com.App.healtcare.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.App.healtcare.data.local.dao.AppInfoDao
import com.App.healtcare.data.local.dao.question.VocabularyDao
import com.App.healtcare.data.local.entity.AppInfoEntity
import com.App.healtcare.data.local.entity.question.VocabularyEntity

@Database(entities = [
    AppInfoEntity::class,
    VocabularyEntity::class
    ], version = 1, exportSchema = false)
abstract class HealtLIfeDatabase : RoomDatabase(){
    abstract fun appInfoDao(): AppInfoDao
    abstract fun vocabularyDao() : VocabularyDao


}