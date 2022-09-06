package com.ptithcm.thuan6420.yam.ui.component.room

import com.google.android.material.tabs.TabLayoutMediator
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.databinding.ActivityRoomBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var tabAdapter: TabRoomAdapter

    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = ""
        tabAdapter = TabRoomAdapter(supportFragmentManager, lifecycle)
        binding.vp2Room.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp2Room) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.information)
                1 -> tab.text = getString(R.string.station)
                2 -> tab.text = getString(R.string.members)
            }
        }.attach()
    }

    override fun initViewBinding() {
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}