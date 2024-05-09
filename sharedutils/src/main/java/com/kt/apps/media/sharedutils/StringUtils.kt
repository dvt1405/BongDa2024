package com.kt.apps.media.sharedutils

fun String.getHost(): String {
    val uri = java.net.URI(this)
    return uri.host
}

fun String.getBaseUrl(): String {
    val uri = java.net.URI(this)
    return uri.scheme + "://" + uri.host
}