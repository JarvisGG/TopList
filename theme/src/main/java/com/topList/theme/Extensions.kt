package com.topList.theme

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import kotlin.math.round

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

/**
 * dp 转 px
 */
val Number.dp2px @JvmName("dp2px") get() = round(toFloat() * Resources.getSystem().displayMetrics.density)
/**
 * sp 转 px
 */
val Number.sp2px @JvmName("sp2px") get() = round(toFloat() * Resources.getSystem().displayMetrics.scaledDensity)

/**
 * px 转 dp
 */
val Number.px2dp @JvmName("px2dp") get() = round(toFloat() / Resources.getSystem().displayMetrics.density)

/**
 * px 转 sp
 */
val Number.px2sp @JvmName("px2sp") get() = round(toFloat() / Resources.getSystem().displayMetrics.scaledDensity)
