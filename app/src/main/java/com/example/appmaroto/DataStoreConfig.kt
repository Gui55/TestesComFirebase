package com.example.appmaroto

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreConfig {

    private val Context.dataStore by preferencesDataStore(name = "token_settings")

    private val SENT_TOKEN = booleanPreferencesKey("sent_token")

    suspend fun changeTokenStatus(context: Context, token: Boolean) {
        context.dataStore.edit { settings ->
            settings[SENT_TOKEN] = token
        }
    }

    suspend fun wasTokenSent(context: Context): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[SENT_TOKEN] ?: false
        }.first()
    }

}