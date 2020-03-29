package com.topList.android.ui.subscribe.model

import com.topList.android.api.model.StationItem

open class Station(
    var isHeader: Boolean
)

data class StationUseItem(
    val type: String,
    val name: String,
    val img: String,
    val id: String,
    val icon: String
) : Station(false) {

    var tag: String = name

    constructor(station: StationItem, tag: String) :
            this(station.type,
                station.name,
                station.img,
                station.id,
                station.icon) {
        this.tag = tag
    }
}

data class StationHeaderUseItem(
    val name: String
) : Station(true) {
    val tag = name
}