package com.topList.android.ui.subscribe.model

import com.topList.android.api.model.StationItem

data class StationUseItem(
    val type: String,
    val name: String,
    val img: String,
    val id: String,
    val icon: String
) {
    var tag: String = ""

    constructor(station: StationItem) :
            this(station.type,
                station.name,
                station.img,
                station.id,
                station.icon) {
        this.tag = station.name
    }
}