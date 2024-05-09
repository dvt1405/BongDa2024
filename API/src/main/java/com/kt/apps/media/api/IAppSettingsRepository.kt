package com.kt.apps.media.api

interface IAppSettingsRepository {
    suspend fun getMapData(key: String): Map<String, String>?
    suspend fun saveMapData(key: String, data: Map<String, String>)
    suspend fun getBoolean(key: String): Boolean
    suspend fun setBoolean(key: String, value: Boolean)
    suspend fun getString(key: String): String?
    suspend fun setString(key: String, value: String)
    suspend fun getInt(key: String): Int
    suspend fun setInt(key: String, value: Int)
    suspend fun getLong(key: String): Long
    suspend fun setLong(key: String, value: Long)
    suspend fun getFloat(key: String): Float
    suspend fun setFloat(key: String, value: Float)
    suspend fun getDouble(key: String): Double
    suspend fun setDouble(key: String, value: Double)

    suspend fun isFirstTimeUser(): Boolean
    suspend fun setFirstTimeUser(isFirstTime: Boolean)
    fun getLanguage(): String
    fun setLanguage(language: String)
    fun privacyPolicyAccepted(): Boolean
    fun setPrivacyPolicyAccepted(accepted: Boolean)
}