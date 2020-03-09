//package com.topList.android
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
//
///**
// * @author yyf
// * @since 08-15-2019
// */
//public inline fun <reified T: Activity> Context.startActivity(
//    vararg params: Pair<String, String>) {
//    val intent = Intent(this, T::class.java)
//    params.forEach { intent.putExtra(it.first, it.second) }
//    startActivity(intent)
//}
//
//inline fun <reified T> LiveData<T>.observeWithFragmentLifecycle(
//    fragment: Fragment,
//    noinline onChanged: (t: T) -> Unit
//): Unit =
//    observe(fragment.viewLifecycleOwner, Observer(onChanged))
//
///*
// ************************ View Model factory methods ************************
// */
//
//inline fun <reified VM : ViewModel> Fragment.getViewModel(
//    factory: ViewModelProvider.Factory? = null
//): VM =
//    ViewModelProviders.of(
//        this,
//        factory
//    ).get(VM::class.java)
//
//inline fun <reified VM : ViewMod  el> Fragment.savedStateViewModelFactory(
//    noinline init: (handle: SavedStateHandle) -> VM
//): Lazy<VM> =
//    viewModels { SavedStateViewModelFactory(this, null, init) }
//
//inline fun <reified VM : ViewModel> Fragment.viewModelFactory(
//    noinline init: () -> VM
//): Lazy<VM> =
//    viewModels { ViewModelFactory(init) }