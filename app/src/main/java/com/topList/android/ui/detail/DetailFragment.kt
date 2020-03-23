package com.topList.android.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.topList.android.R
import com.topList.android.api.model.FeedItem
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * @author yyf
 * @since 03-22-2020
 */
class DetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebSettings()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSettings() {
        webview.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            loadsImagesAutomatically = true
            displayZoomControls = false
            defaultTextEncodingName = "UTF-8"
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

            setSupportZoom(false)
            setSupportMultipleWindows(true)
            setAppCacheEnabled(true)
            setAppCachePath(context!!.cacheDir.absolutePath)

            supportMultipleWindows()
        }

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (request == null || view == null) {
                    return false
                }
                view.loadUrl(request.url.toString())
                return true
            }
        }




    }

    fun populate(data: FeedItem) {
        webview.loadUrl(data.url)
    }

}