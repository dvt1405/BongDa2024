package com.kt.apps.media.core.settings

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kt.apps.media.api.IAppSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toCollection
import javax.inject.Inject

class AppSettings @Inject constructor(
    @ApplicationContext
    private val context: Context
) : IAppSettingsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "app_settings"
    )

    private val _sharedPreferences by lazy {
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    }

    override suspend fun getMapData(key: String): Map<String, String>? {
        return context.dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[stringSetPreferencesKey(key)]?.associate {
                    Log.d("AppSettings", "result: $it")
                    it.split("::").let { list ->
                        list[0] to list[1]
                    }
                }
            }
            .singleOrNull()
    }

    override suspend fun saveMapData(key: String, data: Map<String, String>) {
        context.dataStore.edit {
            it[stringSetPreferencesKey(key)] = data.map {
                "${it.key}::${it.value}"
            }.toSet()
        }
    }

    override suspend fun getBoolean(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getString(key: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun setString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getInt(key: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setInt(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getLong(key: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun setLong(key: String, value: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFloat(key: String): Float {
        TODO("Not yet implemented")
    }

    override suspend fun setFloat(key: String, value: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun getDouble(key: String): Double {
        TODO("Not yet implemented")
    }

    override suspend fun setDouble(key: String, value: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun isFirstTimeUser(): Boolean {
        context.dataStore.data
            .singleOrNull()
            .let {
                Log.d("AppSettings", "data: $it")
            }
        return context.dataStore.data.last()[booleanPreferencesKey("is_first_time")] ?: true
    }

    override suspend fun setFirstTimeUser(isFirstTime: Boolean) {
        context.dataStore.edit {
            it[booleanPreferencesKey("is_first_time")] = isFirstTime
        }
    }

    override fun getLanguage(): String {
        return _sharedPreferences.getString("language", "en") ?: "en"
    }

    override fun setLanguage(language: String) {
        _sharedPreferences.edit()
            .putString("language", language)
            .apply()
    }

    override fun privacyPolicyAccepted(): Boolean {
        return _sharedPreferences.getBoolean("privacy_policy_accepted", false)
    }

    override fun setPrivacyPolicyAccepted(accepted: Boolean) {
        _sharedPreferences.edit()
            .putBoolean("privacy_policy_accepted", accepted)
            .apply()
    }

    companion object {
        private lateinit var instance: AppSettings

        @Synchronized
        fun getInstance(context: Application): AppSettings {
            if (!::instance.isInitialized) {
                instance = AppSettings(context)
            }
            return instance
        }
    }
}