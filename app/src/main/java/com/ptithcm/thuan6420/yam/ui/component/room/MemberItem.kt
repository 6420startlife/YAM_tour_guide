package com.ptithcm.thuan6420.yam.ui.component.room

import android.view.View
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.User
import com.ptithcm.thuan6420.yam.databinding.ItemMembersBinding
import com.xwray.groupie.viewbinding.BindableItem

class MemberItem(val user: User): BindableItem<ItemMembersBinding>() {
    override fun bind(viewBinding: ItemMembersBinding, position: Int) {
        viewBinding.tvNameMember.text = user.fullName
    }

    override fun getLayout() = R.layout.item_members

    override fun initializeViewBinding(view: View) = ItemMembersBinding.bind(view)
}