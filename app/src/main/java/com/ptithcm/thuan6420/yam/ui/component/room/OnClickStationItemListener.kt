package com.ptithcm.thuan6420.yam.ui.component.room

import com.ptithcm.thuan6420.yam.data.dto.Station

interface OnClickStationItemListener {
    fun onClickStationItem(station: Station)
    fun onClickToLocation(address: String)
}