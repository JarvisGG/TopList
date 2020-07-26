package com.topList.android.ui.feed.model

import com.topList.android.api.model.FeedItem
import kotlinx.serialization.Serializable

/**
 * @author yyf
 * @since 04-09-2020
 */
@Serializable
data class CollectModel(
    val title: String = "",
    val desc: String = "",
    val icon: String = "",
    val url: String = ""
)

fun parseCollectModel(data: Any): CollectModel {
    val value = sTransformer[data::class.java]
    assert(value == null)
    val transformer = value as Transformer<Any>
    return transformer.transform(data)
}

val sTransformer = hashMapOf<Class<*>, Transformer<*>>(
    FeedItem::class.java to FeedItemHisTransformer()
)

interface Transformer<T> {
    fun transform(origin: T): CollectModel
}

class FeedItemHisTransformer : Transformer<FeedItem> {
    override fun transform(origin: FeedItem): CollectModel {
        return CollectModel(
            origin.title,
            origin.desc,
            origin.img,
            origin.url
        )
    }

}
