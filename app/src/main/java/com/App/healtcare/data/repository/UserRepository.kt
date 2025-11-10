package com.App.healtcare.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.App.healtcare.data.model.UserInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
   private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys{
        val MIN_RANGE = intPreferencesKey("min_range")
        val MAX_RANGE = intPreferencesKey("max_range")
    }

    //this function will return one object to UserInput
    fun getUserInput(): Flow<UserInput>{
        return dataStore.data.map { preferences ->
            val min = preferences[PreferencesKeys.MIN_RANGE] ?: 1
            val max = preferences[PreferencesKeys.MAX_RANGE] ?: 10

            UserInput(
                min = min,
                max = max
            )
        }

    }
    //option for new setting
    suspend fun saveRangeSettings(min: Int, max: Int){
        dataStore.edit { settings ->
            settings[PreferencesKeys.MIN_RANGE] = min
            settings[PreferencesKeys.MAX_RANGE] = max
        }
    }
}
