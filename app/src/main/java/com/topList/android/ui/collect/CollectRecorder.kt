package com.topList.android.ui.collect

import android.content.Context
import com.topList.android.room.CollectRoomHelper
import com.topList.android.room.HistoryRoomHelper
import com.topList.android.room.model.CollectEntity
import com.topList.android.room.model.HistoryEntity
import com.topList.android.ui.feed.model.CollectModel
import com.topList.android.ui.feed.model.parseCollectModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author yyf
 * @since 04-09-2020
 */
object CollectRecorder {

    fun record(model: Any, scope: CoroutineScope, context: Context) {
        record(parseCollectModel(model), scope, context)
    }

    fun record(model: CollectModel, scope: CoroutineScope, context: Context) {
        scope.launch {
            CollectRoomHelper.openHistoryDatabaseForDao(context)?.insertData(
                CollectEntity().apply {
                    data = model
                }
            )
        }
    }
}