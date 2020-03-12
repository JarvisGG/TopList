package com.topList.theme.base

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.topList.theme.R
import com.topList.theme.ThemeManager
import com.topList.theme.base.utils.ResData
import kotlin.properties.Delegates


/**
 * @author yyf
 * @since 03-10-2020
 * set>defStyleAttr(主题可配置样式)>defStyleRes(默认样式)>NULL(主题中直接指定)
 */
open class AttributeDelegate(
    @NonNull
    val view: View,
    @NonNull @StyleableRes
    val styleable: IntArray = R.styleable.ThemedView
) {
    private val NO_VALUE = 0

    private var styleId: Int = 0
    private var styleResIdMap: IntArray by Delegates.notNull()
    private var rawResIdMap: IntArray by Delegates.notNull()

    init {
        styleResIdMap = IntArray(styleable.size)
        rawResIdMap = IntArray(styleable.size)
    }

    fun save(set: AttributeSet?, defStyleAttr: Int ?= 0, defStyleRes: Int ?= 0) {
        if (set == null) {
            return
        }
        saveStyleRes(set)
        saveAttrRes(set)
        saveRawRes(set, defStyleAttr ?: 0, defStyleRes ?: 0)
    }

    private fun saveStyleRes(attrs: AttributeSet) {
        styleId = attrs.styleAttribute
        if (styleId > 0) {
            val typedArray: TypedArray = view.context.obtainStyledAttributes(styleId, styleable)
            for (index in styleable.indices) {
                val resourceId = typedArray.getResourceId(index, NO_VALUE)
                if (resourceId > 0) {
                    setStyleResId(index, resourceId)
                }
            }
            typedArray.recycle()
        }
    }

    /**
     * 可以不需要像 saveRawRes 严格
     * <color name="GBK02A">@color/BK02</color>
     * <color name="GBK02A">@color/BK06</color>
     * 可以这样桥接表示
     * 这里由于用现成的色组，所以才用这种方式，但推荐 saveRawRes
     */
    private fun saveAttrRes(attrs: AttributeSet) {
        for (index in 0 until attrs.attributeCount) {
            val nameResource = attrs.getAttributeNameResource(index)
            for (attrIndex in styleable.indices) {
                if (nameResource == styleable[attrIndex]) {
                    val resourceId = attrs.getAttributeResourceValue(index, NO_VALUE)
                    setRawResId(attrIndex, resourceId)
                }
            }
        }
    }

    /**
     * 前提 ：GBK 的颜色，value，value-night 都是 #xxxxxx 的颜色表示
     * <color name="GBK02">#1A1A1A</color>
     * <color name="GBK02">#999999</color>
     * 这种方式设置颜色也是 Google 官方用法
     */
    private fun saveRawRes(
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val typedArray = view.context.obtainStyledAttributes(attrs, styleable, defStyleAttr, defStyleRes)
        for (index in styleable.indices) {
            if (getStyleResId(index) <= 0 && getRawResId(index) <= 0) {
                val resourceId = typedArray.getResourceId(index, NO_VALUE)
                if (resourceId > 0) {
                    setRawResId(index, resourceId)
                }
            }
        }
        typedArray.recycle()
    }


    //<editor-fold desc=" ResId 存储 ">
    private fun setStyleResId(attrIndex: Int, resId: Int) {
        if (attrIndex >= styleResIdMap.size) {
            return
        }
        styleResIdMap[attrIndex] = resId
    }

    private fun getStyleResId(attrIndex: Int): Int {
        return if (attrIndex >= styleResIdMap.size) {
            NO_VALUE
        } else styleResIdMap[attrIndex]
    }

    fun setRawResId(attrIndex: Int, resourceId: Int) {
        if (attrIndex >= rawResIdMap.size) {
            return
        }
        rawResIdMap[attrIndex] = resourceId
    }

    private fun getRawResId(attrIndex: Int): Int {
        return if (attrIndex >= rawResIdMap.size) {
            NO_VALUE
        } else rawResIdMap[attrIndex]
    }

    fun getResId(index: Int): Int {
        return getResId(index, NO_VALUE)
    }

    fun getResId(index: Int, defValue: Int): Int {
        val resId = getRawResId(index)
        if (resId > 0) {
            return resId
        }
        val styleResId = getStyleResId(index)
        if (styleResId > 0) {
            return styleResId
        }
        return defValue
    }
    //</editor-fold>

    //<editor-fold desc="Res 获取">
    open fun getDrawable(index: Int): ResData<Drawable?> {
        val resId = getResId(index)
        return if (resId > 0) {
            val drawable = ContextCompat.getDrawable(view.context, resId)
            ResData(drawable, true)
        } else {
            ResData(null, false)
        }
    }

    open fun getDimension(index: Int): ResData<Float?> {
        val resId = getResId(index)
        return if (resId > 0) {
            ResData(view.context.resources.getDimension(resId), true)
        } else {
            ResData(null, false)
        }
    }

    open fun getColor(index: Int): ResData<Int?> {
        val resId = getResId(index)
        return if (resId > 0) {
            val color = ContextCompat.getColor(view.context, resId)
            ResData(color, true)
        } else {
            ResData(0, false)
        }
    }


    open fun getColorStateList(index: Int, fallback: ColorStateList? = null): ResData<ColorStateList?> {
        val resId = getResId(index)
        return if (resId > 0) {
            ResData(ContextCompat.getColorStateList(view.context, resId), true)
        } else {
            ResData(fallback, false)
        }
    }

    open fun getItemColorStateList(index: Int, fallback: ColorStateList? = null): ResData<ColorStateList?> {
        val resId = getResId(index)
        return if (resId > 0) {
            ResData(ContextCompat.getColorStateList(view.context, resId), true)
        } else {
            ResData(fallback, false)
        }
    }

    open fun getCompatTint(): ResData<ColorStateList?> {
        var colorRes = getColorStateList(R.styleable.ThemedView_tintColor)
        if (!colorRes.found) {
            colorRes = getColorStateList(R.styleable.ThemedView_android_tint)
        }
        return colorRes
    }
    //</editor-fold>

    fun resetViewAttr() {
        var themeId: Int = getResId(R.styleable.ThemedView_android_theme)
        if (themeId <= 0) {
            themeId = getResId(R.styleable.ThemedView_theme)
        }
        if (themeId > 0) {
            view.context.theme.applyStyle(themeId, true)
        }
        var drawable = getDrawable(R.styleable.ThemedView_android_background)
        if (drawable.found) {
            view.background = drawable.data
        }
        drawable = getDrawable(R.styleable.ThemedView_android_foreground)
        if (drawable.found) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.foreground = drawable.data
            } else if (view is FrameLayout) {
                view.foreground = drawable.data
            }
        }

    }

    /**
     * 重设TextView的所有属性,目前有textAppearance和textColor
     */
    @SuppressLint("WrongConstant")
    open fun resetTextViewAttr() {
        resetViewAttr()
        if (view is TextView) {
            val textView = view
            try {
                val typeface = textView.typeface
                var textStyle = 0
                if (typeface != null) {
                    textStyle = typeface.style
                }
                val textAppearanceId = getResId(R.styleable.ThemedView_android_textAppearance)
                if (textAppearanceId > 0) {
                    TextViewCompat.setTextAppearance(textView, textAppearanceId)
                }
                if (!EditText::class.java.isInstance(view)) {
                    val colorStateList= getColorStateList(R.styleable.ThemedView_android_textColor)
                    if (colorStateList.found && colorStateList.data != null) {
                        textView.setTextColor(colorStateList.data)
                    }
                }
                // 最后设置 android:textStyle 属性
                textView.setTypeface(typeface, textStyle)
            } catch (e: Exception) {
                ThemeManager.logException(e)
            }
        }
    }

    fun afterReset() {
        view.refreshDrawableState()
    }
}