package com.topList.android.webkit

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.topList.android.R
import com.topList.android.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

open class WebFragment : BaseFragment() {

    lateinit var containerWebView: WebViews

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    open fun getLayoutId(): Int = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        containerWebView = webView

        setWebSettings(webView.settings)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, webTitle: String?) {
                super.onReceivedTitle(view, webTitle)

                if (title() == null && webTitle != null) {
                    toolbar.title = webTitle
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                if (newProgress < 100) {
                    progressBar.visibility =View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }

                if (newProgress >= 70 && javascriptToInject() != null) {
                    webView.evaluateJavascript(javascriptToInject(), null)
                }
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return shouldOverrideUrlLoading(request.url) || super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                if (shouldInterceptRequest(request.url)) {
                    return WebResourceResponse(
                        "text/plain",
                        Charsets.UTF_8.name(),
                        "".byteInputStream(Charsets.UTF_8)
                    )
                }
                return super.shouldInterceptRequest(view, request)
            }
        }

        url()?.let { webView.loadUrl(it) }
    }

    /**
     * 设置 WebSettings
     */
    open fun setWebSettings(settings: WebSettings) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.javaScriptEnabled = false
        settings.domStorageEnabled = true
        settings.displayZoomControls = false
        settings.builtInZoomControls = false
        settings.textZoom = 100
        settings.setAppCacheEnabled(true)
        settings.setAppCachePath(requireContext().getDir("appCache", 0).path)
        settings.savePassword = false
        settings.loadsImagesAutomatically = true
    }

    /**
     * 需要设置的 title
     */
    open fun title(): String? = null

    /**
     * 需要加载的 url
     */
    open fun url(): String? = null

    /**
     * 需要注入的 javascript 代码
     */
    open fun javascriptToInject(): String? = null

    /**
     * 返回 true 则拦截指定 url 跳转
     */
    open fun shouldOverrideUrlLoading(url: Uri): Boolean = false

    /**
     * 返回 true 则拦截指定资源请求
     */
    open fun shouldInterceptRequest(url: Uri): Boolean = false

    override fun onPause() {
        webView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroyView() {
        webView.destroy()
        super.onDestroyView()
    }
}