package com.topList.android.webkit

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import com.topList.android.webkit.delegate.WebViewFunction

/**
 * @author yyf
 * @since 03-22-2020
 */
class WebViews : WebView, WebViewFunction {

    constructor(pContext: Context) : super(pContext)

    constructor(pContext: Context, attrs: AttributeSet) : super(pContext, attrs)

    constructor(pContext: Context, attrs: AttributeSet, styles: Int) : super(pContext, attrs, styles)

}