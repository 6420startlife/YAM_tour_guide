package com.ptithcm.thuan6420.yam.ui.component.tasks

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ptithcm.thuan6420.yam.ui.component.tasks.fragment.HomeFragment
import com.ptithcm.thuan6420.yam.ui.component.tasks.fragment.NotificationsFragment
import com.ptithcm.thuan6420.yam.ui.component.tasks.fragment.SettingsFragment

class TasksViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int) = when (position) {
        1 -> NotificationsFragment()
        2 -> SettingsFragment()
        else -> HomeFragment()
    }
}