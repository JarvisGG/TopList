package com.topList.android.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author yyf
 * @since 03-27-2020
 */
class RxBus private constructor() {
    private val mSubject = PublishSubject.create<Any>()
    fun post(`object`: Any) {
        mSubject.onNext(`object`)
    }

    fun <T> toObservable(eventType: Class<T>?): Observable<T> {
        return mSubject.ofType(eventType)
    }

    fun toObservable(): Observable<Any> {
        return mSubject.hide()
    }

    fun hasObservers(): Boolean {
        return mSubject.hasObservers()
    }

    companion object {
        val instance = RxBus()
    }
}