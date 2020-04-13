package com.basefex.exchange.widget.sheet.action

import com.basefex.exchange.widget.sheet.ActionSheetLayout

/**
 * @author yyf
 * @since 06-01-2019
 */
interface ActionFunction {

    interface StateListener {
        fun onState(@ActionSheetLayout.ShowState state: Int)
    }

    val provideScrollCallback: ActionSheetLayout.INestChildScrollChange

    val provideDirection: @ActionSheetLayout.SheetDirection Int
}