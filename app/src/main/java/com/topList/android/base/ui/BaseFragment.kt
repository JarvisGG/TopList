package com.topList.android.base.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.topList.android.R
import com.topList.android.ui.feed.behavior.BottomNavigationBehavior
import com.topList.android.ui.host.HostFragment
import com.topList.android.utils.RxBus
import com.topList.theme.ThemeManager
import com.topList.theme.base.event.ThemeEvent
import com.topList.theme.base.utils.StatusBarDrawable
import com.topList.theme.base.utils.StatusBarUtil
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author yyf
 * @since 03-23-2020
 */
open class BaseFragment : Fragment() {
    // StatusBar 背景
    private var mStatusBarDrawable: StatusBarDrawable? = null

    private var compositeDisposable = CompositeDisposable()

    /**
     *
     */
    fun findMainNavController() = requireActivity().findNavController(R.id.frag_nav_host)

    /**
     * 默认要用 overlay 启动页面
     */
    fun findOverlayNavController(): NavController? {
        if(parentFragment is HostFragment) {
            return (parentFragment as HostFragment).getOverlayNavController()
        }
        return null
    }

    open fun displayBottomNavigationBar(isShown: Boolean) {
        if (parentFragment is HostFragment) {
            return (parentFragment as HostFragment).displayBottomNavigationBar(isShown)
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invalidateStatusBar()
        RxBus.instance.toObservable(ThemeEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(AndroidLifecycleScopeProvider.from(this))
            .subscribe {
                invalidateStatusBar()
            }
    }

    override fun onResume() {
        super.onResume()
        invalidateStatusBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createOnBackPressedCallback()?.apply {
            requireActivity().onBackPressedDispatcher.addCallback(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mStatusBarDrawable = null
    }

    protected open fun createOnBackPressedCallback(): OnBackPressedCallback? {
        return null
    }

    protected open fun invalidateStatusBar() {
        val rootView = view
        if (!isAdded || isDetached || rootView == null) {
            return
        }
        if (!isSystemUiFullscreen()) {
            if (mStatusBarDrawable == null) {
                mStatusBarDrawable = StatusBarUtil.addStatusBarDrawableToView(rootView, provideStatusBarColor())
            }
            mStatusBarDrawable!!.setColor(provideStatusBarColor())
            mStatusBarDrawable!!.invalidateSelf()
        } else {
            if (mStatusBarDrawable != null) {
                StatusBarUtil.removeStatusBarDrawableFromView(rootView)
                mStatusBarDrawable = null
            }
        }
        StatusBarUtil.setStatusBarLightMode(activity as FragmentActivity)
    }

    @ColorInt
    protected open fun provideStatusBarColor(): Int {
        return ContextCompat.getColor(context!!, if (ThemeManager.isDark()) R.color.BK01 else R.color.BK08)
    }

    protected open fun isSystemUiFullscreen() = false

}