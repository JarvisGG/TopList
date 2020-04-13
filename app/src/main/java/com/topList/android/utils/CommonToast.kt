package com.topList.android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.CheckResult
import androidx.annotation.IntDef
import com.topList.android.R
import kotlinx.android.synthetic.main.widget_toast.view.*

class CommonToast private constructor(
    val icon: Int,
    val title: CharSequence,
    val duration: Int
) {

    @SuppressLint("InflateParams")
    fun show(context: Context) {
        val st = android.widget.Toast(context)
        st.view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null).apply {
            toastIcon.setImageResource(icon)
            toastMessage.text = title
        }
        st.setGravity(Gravity.CENTER, 0, 0)
        st.duration = duration
        st.show()
    }

    class Builder(val icon: Int, val title: CharSequence) {
        private var duration: Int = DURATION_LONG

        constructor(presetIcon: PresetIcon, title: CharSequence) : this(presetIcon.resId, title)

        fun duration(@Duration duration: Int): Builder = apply {
            this.duration = duration
        }

        @CheckResult(suggest = "#show(context)")
        fun build(): CommonToast = CommonToast(icon, title, duration)
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(DURATION_SHORT, DURATION_LONG)
    annotation class Duration

    enum class PresetIcon(val resId: Int) {
        ALERT(R.drawable.ic_toast_alert),
        COMPLETE(R.drawable.ic_toast_complete),
        ERROR(R.drawable.ic_toast_error)
    }

    companion object {
        const val DURATION_SHORT: Int = android.widget.Toast.LENGTH_SHORT
        const val DURATION_LONG: Int = android.widget.Toast.LENGTH_LONG
    }

}