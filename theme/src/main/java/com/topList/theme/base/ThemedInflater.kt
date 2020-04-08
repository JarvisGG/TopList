package com.topList.theme.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.topList.theme.base.widget.*

/**
 * @author yyf
 * @since 03-11-2020
 */
object ThemedInflater {

    fun installViewFactory(activity: AppCompatActivity) {
        val delegate = activity.delegate
        if (delegate is LayoutInflater.Factory2) {
            val wrapper = LayoutInflaterFactoryWrapper(delegate, activity.window)
            val inflater = LayoutInflater.from(activity)
            if (inflater.factory2 == null) {
                LayoutInflaterCompat.setFactory2(inflater, wrapper)
            }
        }
    }

    fun installViewFactory(inflater: LayoutInflater) {
        val wrapper = LayoutInflaterFactoryWrapper(inflater.factory2, null)
        LayoutInflaterCompat.setFactory2(inflater, wrapper)
    }

    class LayoutInflaterFactoryWrapper(
        private val originFactory: LayoutInflater.Factory2,
        private val window: Window?
    ): LayoutInflater.Factory2 {

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            return onCreateView(null, name, context, attrs)
        }

        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            var view = replaceView(parent, name, context, attrs)
            if (view == null) {
                view = originFactory.onCreateView(parent, name, context, attrs)
            }
            return view
        }

        private fun replaceView(parent: View?,
                                name: String,
                                context: Context,
                                attrs: AttributeSet
        ) : View? {
            return when (name) {
                "com.google.android.material.bottomnavigation.BottomNavigationView" -> TBottomNavigationView(context, attrs)
                "Button" -> TButton(context, attrs)
                "android.support.v7.widget.CardView",
                "androidx.cardview.widget.CardView" -> TCardView(context, attrs)
                "android.support.constraint.ConstraintLayout",
                "androidx.constraintlayout.widget.ConstraintLayout" -> TConstraintlayout(context, attrs)
                "FrameLayout" -> TFrameLayout(context, attrs)
                "LinearLayout" -> TLinearLayout(context, attrs)
                "android.support.v7.widget.RecyclerView",
                "androidx.recyclerview.widget.RecyclerView" -> TRecyclerView(context, attrs)
                "RelativeLayout" -> TRelativeLayout(context, attrs)
                "Switch" -> TSwitch(context, attrs)
                "TextView" -> TTextView(context, attrs)
                "View" -> TView(context, attrs)
                "android.support.v4.view.ViewPager",
                "androidx.viewpager.widget.ViewPager" -> TViewPager(context, attrs)
                "androidx.appcompat.widget.AppCompatImageView",
                "ImageView" -> TImageView(context, attrs)
                "androidx.appcompat.widget.Toolbar" -> TToolbar(context, attrs)
                else -> null
            }
        }



    }
}