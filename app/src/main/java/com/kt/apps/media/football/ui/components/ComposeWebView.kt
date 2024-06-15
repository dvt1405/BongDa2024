package com.kt.apps.media.football.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.CookieManager
import android.webkit.HttpAuthHandler
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import org.jsoup.Jsoup

class MyJavaScriptInterface(private val context: Context) {
    @JavascriptInterface
    fun processHTML(html: String) {
        Log.d("HTML", html)
        val doc = Jsoup.parse(html)
        val link = doc.select(".mh-buttons > a").first()?.attr("href")
        Log.d("HTML_MHButton", "$link")
    }
}

// In your WebView setup code
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ComposeWebView(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            this.settings.javaScriptEnabled = true
            this.createPrintDocumentAdapter("https://xoilac16.org/home?utm_source=landing&amp;utm_medium=landing")
            this.settings.blockNetworkLoads = false
            this.settings.domStorageEnabled = true
            this.settings.cacheMode = android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
            this.settings.setSupportZoom(true)
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowContentAccess = true
            addJavascriptInterface(MyJavaScriptInterface(context), "HTMLOUT")
            this.webChromeClient = object : WebChromeClient() {
                override fun onGeolocationPermissionsHidePrompt() {
                    super.onGeolocationPermissionsHidePrompt()
                }

                override fun onPermissionRequest(request: PermissionRequest?) {
                    super.onPermissionRequest(request)
                }

                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    super.onShowCustomView(view, callback)
                }

                override fun onRequestFocus(view: WebView?) {
                    super.onRequestFocus(view)
                }

                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    super.onReceivedIcon(view, icon)
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    Log.e("TAG", "onProgressChanged: $newProgress")
                }
            }
            this.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.e("TAG", "======================onPageFinished: $url")

                    url?.let {
                        CookieManager.getInstance()
                            .getCookie(it)
                            ?.let { cookie ->
                                Log.e("TAG", "Cookie onPageFinished: $cookie")
                            }
                    }
                }

                override fun onReceivedHttpAuthRequest(
                    view: WebView?,
                    handler: HttpAuthHandler?,
                    host: String?,
                    realm: String?
                ) {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm)
                }

                override fun onReceivedClientCertRequest(
                    view: WebView?,
                    request: ClientCertRequest?
                ) {
                    super.onReceivedClientCertRequest(view, request)
                }

                override fun onReceivedLoginRequest(
                    view: WebView?,
                    realm: String?,
                    account: String?,
                    args: String?
                ) {
                    super.onReceivedLoginRequest(view, realm, account, args)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }


                override fun onFormResubmission(
                    view: WebView?,
                    dontResend: Message?,
                    resend: Message?
                ) {
                    super.onFormResubmission(view, dontResend, resend)
                }
            }
            loadUrl(url)
        }
    }, update = { view ->
        view.loadUrl(url)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeWebView("https://xoilac16.org/home?utm_source=landing&amp;utm_medium=landing")
}
