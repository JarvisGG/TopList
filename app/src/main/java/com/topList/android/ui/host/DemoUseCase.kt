package com.topList.android.ui.host

import com.topList.android.ui.HostFragment

/**
 * @author yyf
 * @since 03-26-2020
 */
interface IDemoUseCase {
    fun do1()
    fun do2()
}
class DemoUseCase : IDemoUseCase {

    private lateinit var host: HostFragment

    private lateinit var delegate: MainDelegate

    operator fun invoke(fragment: HostFragment, dlg: MainDelegate) {
        host = fragment
        delegate = dlg
    }

    override fun do1() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun do2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}