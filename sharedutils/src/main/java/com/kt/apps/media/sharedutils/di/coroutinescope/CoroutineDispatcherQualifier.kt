package com.kt.apps.media.sharedutils.di.coroutinescope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineDispatcherQualifier(
    val value: CoroutineDispatcherType
) {
}

enum class CoroutineDispatcherType {
    IO, DEFAULT, MAIN
}