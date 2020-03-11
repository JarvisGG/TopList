package com.topList.theme

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.core.content.ContextCompat.getSystemService

/**
 * @author yyf
 * @since 03-11-2020
 */
val View.bitmap : Bitmap?
    get() {
        destroyDrawingCache()
        isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(this.drawingCache)
        isDrawingCacheEnabled = false
        return bitmap
    }


val Context.screenWidthPixels : Int
    get() = resources?.displayMetrics?.widthPixels ?: 0

val Context.screenHeightPixels : Int
    get() = resources?.displayMetrics?.heightPixels ?: 0
