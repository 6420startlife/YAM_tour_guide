package com.ptithcm.thuan6420.yam.ui.component.tasks.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptithcm.thuan6420.yam.databinding.FragmentNotificationsBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel
import com.ptithcm.thuan6420.yam.ui.component.tasks.NotificationItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private val viewModel: StationViewModel by viewModels()
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(layoutInflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = groupAdapter
        }
        groupAdapter.clear()
        viewModel.getNotifications().listNotification.forEach{
            groupAdapter.add(NotificationItem(it.title, it.message))
        }
    }
}