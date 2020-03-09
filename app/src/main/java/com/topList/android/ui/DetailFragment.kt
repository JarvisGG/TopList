package com.topList.android.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.topList.android.R
import kotlinx.android.synthetic.main.fragment_detail.*
import android.webkit.WebSettings
import androidx.navigation.fragment.navArgs
import com.helpchoice.kotlin.koton.kotON
import com.topList.android.ui.DetailFragmentArgs


class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()

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
        "asd"[1]

        val doc = kotON( {
            "string" to "string value"
            "integer" to 42
            "array"[
                    { "stringElement" to "value of an element" },
                    { "intKey" to 42; "floatKey" to 3.14 },
                    {
                        "boolTrue" to true
                        "booleFalse" to false
                    }
            ]
            "float" to 3.14
            "boolean true" to true
            "subStruct" {
                "substring" to "string value"
                "subinteger" to 42
                "subfloat" to 3.14
                "subarray"[
                        { "stringElement" to "value of an element" },
                        { "intKey" to 42; "floatKey" to 3.14 },
                        {
                            "boolTrue" to true
                            "booleFalse" to false
                        }
                ]
                "subboolean true" to true
                "subboolean false" to false
            }
            "boolean false" to false
        }, {
            "string" to "string value"
            "integer" to 42
            "array"[
                    { "stringElement" to "value of an element" },
                    { "intKey" to 42; "floatKey" to 3.14 },
                    {
                        "boolTrue" to true
                        "booleFalse" to false
                    }
            ]
            "float" to 3.14
            "boolean true" to true
            "subStruct" {
                "substring" to "string value"
                "subinteger" to 42
                "subfloat" to 3.14
                "subarray"[
                        { "stringElement" to "value of an element" },
                        { "intKey" to 42; "floatKey" to 3.14 },
                        {
                            "boolTrue" to true
                            "booleFalse" to false
                        }
                ]
                "subboolean true" to true
                "subboolean false" to false
            }
            "boolean false" to false
        }).toJson()

        webview.loadUrl(args.feedItem.url)
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
    }
}
