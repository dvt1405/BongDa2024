package com.kt.apps.media.api.livescore

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface XoilacZCOAPI {
    @GET("/livedata/live.json")
    suspend fun liveScore(
        @HeaderMap headers: Map<String, String> = mapOf()
    ): ResponseBody
}