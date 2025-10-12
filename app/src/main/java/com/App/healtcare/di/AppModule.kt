package com.App.healtcare.di

import android.app.Application
import androidx.room.Room
import com.App.healtcare.data.local.HealtLIfeDatabase
import com.App.healtcare.data.local.dao.AppInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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