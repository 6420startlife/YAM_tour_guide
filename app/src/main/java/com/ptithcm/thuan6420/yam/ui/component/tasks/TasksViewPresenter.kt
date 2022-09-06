package com.ptithcm.thuan6420.yam.ui.component.tasks

import android.view.MenuItem
import com.ptithcm.thuan6420.yam.R

class TasksViewPresenter(private val viewPagerInterface : ViewPagerInterface) {
    fun changeViewPager(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_item_home -> viewPagerInterface.switchPageSelected(0)
            R.id.menu_item_notifications -> viewPagerInterface.switchPageSelected(1)
            R.id.menu_item_settings -> viewPagerInterface.switchPageSelected(2)
            else -> viewPagerInterface.switchPageSelected(0)
        }
    }

    fun changeViewPagerOnCallBack(position: Int) {
        when (position) {
            1 -> viewPagerInterface.changePageOnCallback(R.id.menu_item_notifications)
            2 -> viewPagerInterface.changePageOnCallback(R.id.menu_item_settings)
            0 -> viewPagerInterface.changePageOnCallback(R.id.menu_item_home)
            else -> viewPagerInterface.changePageOnCallback(R.id.menu_item_home)
        }
    }
}