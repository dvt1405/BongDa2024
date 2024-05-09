package com.kt.apps.media.sharedutils.exceptions

class NetworkExceptions(
    override val errorCode: Int,
    override val message: String? = null,
    override val cause: Throwable? = null
) : MyExceptions(
    errorCode, message, cause
) {
}