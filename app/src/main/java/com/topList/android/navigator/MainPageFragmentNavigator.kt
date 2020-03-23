package com.topList.android.navigator

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.R
import java.util.*

/**
 * @author yyf
 * @since 03-23-2020
 */
@Navigator.Name("page")
class MainPageFragmentNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : Navigator<MainPageFragmentNavigator.Destination>() {

    private val TAG = "MainPageFragmentNavigator"
    private val PAGE_TAG = "androidx-nav-fragment:navigator:page:id:"
    private val PAGE_STACK_TAG = "androidx-nav-fragment:navigator:page:stack"

    private var stack: ArrayDeque<Int> = ArrayDeque()

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (manager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state")
            return null
        }

        val tag = PAGE_TAG + destination.id
        val ft = manager.beginTransaction()
//        var frag = manager.findFragmentByTag(tag)
//        if (frag == null) {
            val frag = createFragment(destination, args, manager)
            if (navigatorExtras is FragmentNavigator.Extras) {
                for ((key, value) in navigatorExtras.sharedElements) {
                    ft.addSharedElement(key!!, value!!)
                }
            }
            stack.addLast(destination.id)
            ft.add(containerId, frag, tag)
//        } else {
//            if (manager.primaryNavigationFragment != null) {
//                ft.hide(manager.primaryNavigationFragment!!)
//            }
//            ft.show(frag)
//            alreadyExist = false
//        }
        ft.setPrimaryNavigationFragment(frag)
        ft.commit()

        return destination
    }

    override fun popBackStack(): Boolean {
        if (manager.isStateSaved) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already" + " saved its state")
            return false
        }
        val tag = stack.pollLast()
        val existingFragment = manager
            .findFragmentByTag(PAGE_TAG + tag)
        if (existingFragment != null) {
            manager.beginTransaction().remove(existingFragment).commitNowAllowingStateLoss()
        }
        return true
    }

    override fun onSaveState(): Bundle? {
        val b = Bundle()
        val backStack = IntArray(stack.size)
        var index = 0
        for (id in stack) {
            backStack[index++] = id!!
        }
        b.putIntArray(PAGE_STACK_TAG, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        savedState.let {
            val backStack = savedState.getIntArray(PAGE_STACK_TAG)
            if (backStack != null) {
                stack.clear()
                for (destId in backStack) {
                    stack.add(destId)
                }
            }
        }
    }


    private fun createFragment(
        destination: Destination,
        args: Bundle?,
        manager: FragmentManager
    ): Fragment {
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val frag = manager.fragmentFactory.instantiate(context.classLoader, className)
        frag.arguments = args
        return frag
    }


    @NavDestination.ClassType(Fragment::class)
    class Destination : NavDestination {

        var className: String = ""
            get() {
                if (TextUtils.isEmpty(field)) {
                    throw IllegalStateException("Fragment class was not set")
                }
                return field
            }

        constructor(navigatorProvider: NavigatorProvider) : super(
            navigatorProvider.getNavigator<MainPageFragmentNavigator>(
                MainPageFragmentNavigator::class.java
            ))

        constructor(fragmentNavigator: Navigator<out Destination>) : super(fragmentNavigator)


        @CallSuper
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            val a = context.resources.obtainAttributes(
                attrs,
                R.styleable.FragmentNavigator
            )
            className = a.getString(R.styleable.FragmentNavigator_android_name) ?: ""
            a.recycle()
        }
    }
}