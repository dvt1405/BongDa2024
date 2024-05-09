package com.kt.apps.media.sharedutils.exceptions

open class MyExceptions(
    open val errorCode: Int,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause) {
}