package com.kt.apps.media.sharedutils.di.api

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class APIQualifier(
    val source: APISource
) {
}

enum class APISource(val value: String) {
    XoilacZCO("https://spapi.p2pcdn.xyz/"),
}