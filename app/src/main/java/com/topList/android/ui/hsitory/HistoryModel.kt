package com.topList.android.ui.hsitory

import com.topList.android.api.model.FeedItem
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 04-09-2020
 */
@Serializable
data class HistoryModel(
    val title: String = "",
    val icon: String = "",
    val url: String = ""
)

fun parseHistoryModel(data: Any): HistoryModel {
    val value = sTransformer[data::class.java]
    assert(value == null)
    val transformer = value as Transformer<Any>
    return transformer.transform(data)
}

val sTransformer = hashMapOf<Class<*>, Transformer<*>>(
    FeedItem::class.java to FeedItemHisTransformer()
)

interface Transformer<T> {
    fun transform(origin: T): HistoryModel
}

class FeedItemHisTransformer : Transformer<FeedItem> {
    override fun transform(origin: FeedItem): HistoryModel {
        return HistoryModel(
            origin.title,
            origin.img,
            origin.url
        )
    }

}
