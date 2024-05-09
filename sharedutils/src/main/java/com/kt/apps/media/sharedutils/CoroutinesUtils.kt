package com.kt.apps.media.sharedutils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun <T, R> transformDeferred(original: Deferred<T>, transform: (T) -> R): Deferred<R> {
    return CoroutineScope(Dispatchers.IO).async {
        val originalResult = original.await()
        transform(originalResult)
    }
}