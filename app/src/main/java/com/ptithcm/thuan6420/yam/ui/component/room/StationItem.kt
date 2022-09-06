package com.ptithcm.thuan6420.yam.ui.component.room

import android.view.View
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.Station
import com.ptithcm.thuan6420.yam.databinding.ItemStationBinding
import com.xwray.groupie.viewbinding.BindableItem

class StationItem(val station: Station, val listener: OnClickStationItemListener) :
    BindableItem<ItemStationBinding>() {

    override fun bind(viewBinding: ItemStationBinding, position: Int) {
        viewBinding.tvNameStation.text = station.name
        viewBinding.tvDateOfStopStation.text = "${station.timeStop} ${station.dateOfStop}"
        viewBinding.itemStation.setOnClickListener {
            listener.onClickStationItem(station)
        }
        viewBinding.ivLocation.setOnClickListener {
            listener.onClickToLocation(station.address)
        }
    }

    override fun getLayout() = R.layout.item_station

    override fun initializeViewBinding(view: View) = ItemStationBinding.bind(view)
}