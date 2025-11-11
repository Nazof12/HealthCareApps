package com.App.healtcare.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.App.healtcare.data.local.HealtLIfeDatabase
import com.App.healtcare.data.local.dao.AppInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.dataStore
    }
    @Provides
    @Singleton
    fun provideHeathDatabase( app: Application): HealtLIfeDatabase{
        return Room.databaseBuilder(
            app,
            HealtLIfeDatabase::class.java,
            "health_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppInfoDao(db: HealtLIfeDatabase): AppInfoDao{
        return db.appInfoDao()
    }
}