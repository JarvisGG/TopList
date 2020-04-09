package com.topList.android.ui.hsitory

import android.content.Context
import com.topList.android.room.HistoryRoomHelper
import com.topList.android.room.model.HistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author yyf
 * @since 04-09-2020
 */
object HistoryRecorder {

    fun record(model: Any, scope: CoroutineScope, context: Context) {
        record(parseHistoryModel(model), scope, context)
    }

    fun record(model: HistoryModel, scope: CoroutineScope, context: Context) {
        scope.launch {
            HistoryRoomHelper.openHistoryDatabaseForDao(context)?.insertData(
                HistoryEntity().apply {
                    data = model
                }
            )
        }
    }
}