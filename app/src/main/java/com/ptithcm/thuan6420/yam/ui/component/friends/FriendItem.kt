package com.ptithcm.thuan6420.yam.ui.component.friends

import android.view.View
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.User
import com.ptithcm.thuan6420.yam.databinding.ItemFiendsBinding
import com.xwray.groupie.viewbinding.BindableItem

class FriendItem(val user: User): BindableItem<ItemFiendsBinding>() {
    override fun bind(viewBinding: ItemFiendsBinding, position: Int) {
        viewBinding.tvNameFriend.text = user.fullName
    }

    override fun getLayout() = R.layout.item_fiends

    override fun initializeViewBinding(view: View) = ItemFiendsBinding.bind(view)
}