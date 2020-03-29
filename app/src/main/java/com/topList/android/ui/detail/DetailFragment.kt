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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundle()
    }

    private fun initBundle() {
        val url = try {
            args.url
        } catch (e: Exception) {
            ""
        }
        if (url.isNotEmpty()) {
            populate(url)
        }
    }

    fun populate(url: String) {
        containerWebView.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setWebSettings(settings: WebSettings) {
        super.setWebSettings(settings)
        settings.javaScriptEnabled = true
    }

    override fun shouldOverrideUrlLoading(url: Uri) = true

}