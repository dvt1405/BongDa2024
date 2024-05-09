package com.kt.apps.media.api

interface IConfigRepository {
    fun getRemoteConfigJson(): String
    fun getRemoteConfigWithKey(key: String): String
}