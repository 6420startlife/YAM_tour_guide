package com.ptithcm.thuan6420.yam.ui.component.tasks

import android.view.View
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.databinding.ItemNotificationsBinding
import com.xwray.groupie.viewbinding.BindableItem

class NotificationItem(val title: String, val description: String): BindableItem<ItemNotificationsBinding>() {
    override fun bind(viewBinding: ItemNotificationsBinding, position: Int) {
        viewBinding.tvTitleNotification.text = title
        viewBinding.tvNotificationDescription.text = description
    }

    override fun getLayout() = R.layout.item_notifications

    override fun initializeViewBinding(view: View) = ItemNotificationsBinding.bind(view)
}