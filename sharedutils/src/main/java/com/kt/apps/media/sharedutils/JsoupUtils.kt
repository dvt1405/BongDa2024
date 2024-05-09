package com.kt.apps.media.sharedutils

import android.util.Log
import com.kt.apps.media.sharedutils.exceptions.NetworkExceptions
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

data class JsoupResponse(
    val responseUrl: String,
    val body: Element,
    val cookie: Map<String, String>
)

private const val MAX_RETRY_COUNT = 2

fun jsoupConnect(
    url: String,
    cookie: Map<String, String>,
    vararg header: Pair<String, String>,
    retry: Int = MAX_RETRY_COUNT
): Connection {
    if (retry == 0) throw NetworkExceptions(ErrorCode.ERROR_NETWORK)
    return try {
        Jsoup.connect(url)
            .followRedirects(true)
            .header(Constants.HEADER_USER_AGENT, Constants.HEADER_USER_AGENT_CHROME)
            .apply {
                header.forEach {
                    this.header(it.first, it.second)
                }
            }
            .cookies(cookie)
    } catch (e: InterruptedException) {
        jsoupConnect(url, cookie, *header)
    }

}

fun jsoupParse(
    url: String,
    cookie: Map<String, String>,
    vararg header: Pair<String, String>
): JsoupResponse {
    Log.d("Jsoup", url)
    trustEveryone()
    val connection = jsoupConnect(url, cookie, *header)
        .timeout(30_000)
        .followRedirects(true)
        .execute()
    Log.d("Jsoup", "Response URL: ${connection.url()}")
    Log.d("Jsoup", "${connection.statusCode()}")
    Log.d("Jsoup", connection.statusMessage())

    val body = connection.parse().body()
    return JsoupResponse(
        connection.url().toString(),
        body,
        mutableMapOf<String, String>().apply {
            putAll(cookie)
            putAll(connection.cookies())
        }
    )
}