package com.topList.theme.base.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.util.LongSparseArray
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatDrawableManager
import androidx.appcompat.widget.ResourceManagerInternal
import androidx.appcompat.widget.TintContextWrapper
import com.topList.theme.ThemeManager
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.util.*

/**
 * @author yyf
 * @since 03-10-2020
 */
internal object ResourceFlusher {
    fun flush(@NonNull resources: Resources) {
        if (Build.VERSION.SDK_INT < 21) {
            flushKitKat(resources)
        } else if (Build.VERSION.SDK_INT < 23) {
            flushLollipop(resources)
        }
        flushAppCompat()
    }

    private fun flushKitKat(resources: Resources) {
        try {
            val sDrawableCacheField =
                Resources::class.java.getDeclaredField("mDrawableCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache =
                sDrawableCacheField[resources] as LongSparseArray<*>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val sDrawableCacheField =
                Resources::class.java.getDeclaredField("mColorStateListCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache =
                sDrawableCacheField[resources] as LongSparseArray<*>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val sDrawableCacheField =
                Resources::class.java.getDeclaredField("mColorDrawableCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache =
                sDrawableCacheField[resources] as LongSparseArray<*>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
    }

    private fun flushLollipop(resources: Resources) {
        try {
            val sDrawableCacheField =
                Resources::class.java.getDeclaredField("mDrawableCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache =
                sDrawableCacheField[resources] as MutableMap<*, *>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val sDrawableCacheField = Resources::class.java.getDeclaredField("mColorStateListCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache = sDrawableCacheField[resources] as LongSparseArray<*>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val sDrawableCacheField = Resources::class.java.getDeclaredField("mColorDrawableCache")
            sDrawableCacheField.isAccessible = true
            val drawableCache = sDrawableCacheField[resources] as MutableMap<*, *>
            drawableCache.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun flushAppCompat() {
        try {
            val field: Field = AppCompatResources::class.java.getDeclaredField("sColorStateCaches")
            field.isAccessible = true
            val map = field[AppCompatResources::class.java] as MutableMap<*, *>
            map.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val field: Field = ResourceManagerInternal::class.java.getDeclaredField("mDrawableCaches")
            field.isAccessible = true
            val map = field[ResourceManagerInternal.get()] as MutableMap<*, *>
            map.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val field: Field =
                ResourceManagerInternal::class.java.getDeclaredField("mTintLists")
            field.isAccessible = true
            val map =
                field[ResourceManagerInternal.get()] as MutableMap<*, *>
            map.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
        try {
            val field: Field = TintContextWrapper::class.java.getDeclaredField("sCache")
            field.isAccessible = true
            val list: ArrayList<WeakReference<TintContextWrapper>> =
                field[TintContextWrapper::class.java] as ArrayList<WeakReference<TintContextWrapper>>
            for (reference in list) {
                reference.clear()
            }
            list.clear()
        } catch (e: Exception) {
            ThemeManager.logException(e)
        }
    }
}