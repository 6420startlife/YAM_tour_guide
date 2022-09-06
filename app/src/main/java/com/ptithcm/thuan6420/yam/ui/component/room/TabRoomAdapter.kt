package com.ptithcm.thuan6420.yam.ui.component.room

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ptithcm.thuan6420.yam.ui.component.room.fragment.MembersFragment
import com.ptithcm.thuan6420.yam.ui.component.room.fragment.RoomFragment
import com.ptithcm.thuan6420.yam.ui.component.room.fragment.StationFragment

class TabRoomAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int) = when (position) {
        1 -> StationFragment()
        2 -> MembersFragment()
        else -> RoomFragment()
    }
}