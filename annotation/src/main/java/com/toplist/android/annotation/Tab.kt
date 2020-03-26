package com.toplist.android.annotation

import java.lang.annotation.Inherited

/**
 * @author yyf
 * @since 03-26-2020
 */
@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Tab(val titleRes: Int, val iconRes: Int, val position: Int)