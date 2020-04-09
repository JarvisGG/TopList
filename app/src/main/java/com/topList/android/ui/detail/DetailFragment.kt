package com.topList.android.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.topList.android.R
import com.topList.android.webkit.WebFragment
import com.topList.android.webkit.customtabs.CustomTabActivityHelper
import com.topList.theme.ThemeManager

/**
 * @author yyf
 * @since 03-22-2020
 */
class DetailFragment : WebFragment() {

    private val args by navArgs<DetailFragmentArgs>()

    private var inner: Boolean = false

    private var url: String = ""

    private val helper = CustomTabActivityHelper()


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
        setClearHistory(true)

        inner = inr
        if (inr) {
            invalidateStatusBar()
        }
    }

    fun block() {
        containerWebView.loadUrl("about:blank")
    }


    fun canGoBack(): Boolean {
        return containerWebView.canGoBack()
    }

    fun goBack() {
        containerWebView.goBack()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setWebSettings(settings: WebSettings) {
        super.setWebSettings(settings)
        settings.javaScriptEnabled = true
    }

    override fun shouldOverrideUrlLoading(uri: Uri): Boolean {
        if (uri.toString().startsWith("http") || uri.toString().startsWith("https")) {
            return false
        }
        return true
    }

    override fun javascriptToInject(): String? {
        if (ThemeManager.isDark()) {
            return "document.body.style.backgroundColor=\"#222222\";document.getElementsByTagName('body')[0].style.webkitTextFillColor= '#8a8a8a';"
        }
        return null
    }

    override fun isSystemUiFullscreen() = inner

    private fun openLocalBrowser(uri: Uri) {
        CustomTabActivityHelper.openCustomTab(
            requireActivity(),
            CustomTabsIntent.Builder(helper.session)
                .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.GBK99A))
                .setShowTitle(true)
                .enableUrlBarHiding()
                .addDefaultShareMenuItem()
                .build(),
            uri
        )
    }
}