package com.topList.android.ui.host

import android.os.Bundle
import android.view.View
import com.topList.android.ui.HostFragment

/**
 * @author yyf
 * @since 03-26-2020
 * @desc 用来 useCase 相互调用
 */
class MainDelegate(
    val fragment: HostFragment,
    val tabUseCase: TabUseCase = TabUseCase(),
    val demoUseCase: DemoUseCase = DemoUseCase()
) : IMainDelegate,
    ITabUseCase by tabUseCase,
    IDemoUseCase by demoUseCase {

    init {
        tabUseCase(fragment, this)
        demoUseCase(fragment, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabUseCase.setupTabPage()
        tabUseCase.setupNavigationWidget()
    }
}