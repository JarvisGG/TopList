package com.topList.theme.base.utils

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

/**
 * @author yyf
 * @since 03-10-2020
 */
open class SimpleActivityLifecycleCallbacks : ActivityLifecycleCallbacks {
    override fun onActivityCreated(
        activity: Activity?,
        savedInstanceState: Bundle?
    ) {
    }

    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(
        activity: Activity?,
        outState: Bundle?
    ) {
    }

    override fun onActivityDestroyed(activity: Activity?) {}
}