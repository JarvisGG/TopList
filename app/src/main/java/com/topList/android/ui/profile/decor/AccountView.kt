package com.topList.android.ui.profile.decor

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.topList.theme.base.widget.TFrameLayout

/**
 * @author yyf
 * @since 03-19-2020
 */
class AccountView : TFrameLayout {

    constructor(pContext: Context) : super(pContext)

    constructor(pContext: Context, attrs: AttributeSet) : this(pContext, attrs, 0)

    constructor(pContext: Context, attrs: AttributeSet, styles: Int) : super(pContext, attrs, styles) {
        if (isInEditMode) {
            return
        }
    }


}