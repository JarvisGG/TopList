package com.topList.android.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.navigation.fragment.navArgs
import com.topList.android.webkit.WebFragment

/**
 * @author yyf
 * @since 03-22-2020
 */
class DetailFragment : WebFragment() {

    private val args by navArgs<DetailFragmentArgs>()

    private var inner: Boolean = false

    private var url: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundle()
    }

    private fun initBundle() {
         try {
            url = args.url
            inner = args.inner
        } catch (e: Exception) {
        }
        if (url.isNotEmpty()) {
            populate(url, inner)
        }
    }

    /**
     * inr : 是否是作为来源的子页面
     */
    fun populate(url: String, inr: Boolean) {
        containerWebView.loadUrl(url)
        inner = inr
        if (inr) {
            invalidateStatusBar()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setWebSettings(settings: WebSettings) {
        super.setWebSettings(settings)
        settings.javaScriptEnabled = true
    }

    override fun shouldOverrideUrlLoading(url: Uri) = true

    override fun isSystemUiFullscreen() = inner
}