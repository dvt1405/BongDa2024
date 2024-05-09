package com.kt.apps.media.api

import kotlinx.coroutines.Deferred

interface IWebsiteLinkFinderRepository {
    suspend fun findWebsiteLink(name: String): Deferred<String>
}