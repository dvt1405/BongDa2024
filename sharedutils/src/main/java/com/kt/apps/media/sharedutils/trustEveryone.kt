package com.kt.apps.media.sharedutils

import android.annotation.SuppressLint
import android.util.Log
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@SuppressLint("TrustAllX509TrustManager")
fun trustEveryone() {
    try {
        HttpsURLConnection.setDefaultHostnameVerifier { name, password ->
//            if (BuildConfig.DEBUG) {
//                Log.d("TrustEveryone", "name: $name, password: $password")
//            }
            return@setDefaultHostnameVerifier true
        }
        val context = SSLContext.getInstance("TLS")
        context.init(
            null, arrayOf<X509TrustManager>(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }), SecureRandom()
        )
        HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
    } catch (e: java.lang.Exception) {
//        if (BuildConfig.DEBUG) {
//            Log.e("TrustEveryone", e.message, e)
//        }
    }
}
