package com.ptithcm.thuan6420.yam.ui.component.tasks

import android.view.View
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.Room
import com.ptithcm.thuan6420.yam.databinding.ItemRoomBinding
import com.xwray.groupie.viewbinding.BindableItem

class RoomItem(val room: Room, val listener: OnClickRoomItemListener): BindableItem<ItemRoomBinding>() {
    override fun bind(viewBinding: ItemRoomBinding, position: Int) {
        viewBinding.tvNameRoom.text = room.name
        viewBinding.itemRoom.setOnClickListener{
            listener.onClickRoomItem(room)
        }
    }

    override fun getLayout(): Int = R.layout.item_room

    override fun initializeViewBinding(view: View) = ItemRoomBinding.bind(view)
}