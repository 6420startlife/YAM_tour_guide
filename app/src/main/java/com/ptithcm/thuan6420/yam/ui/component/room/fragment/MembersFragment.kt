package com.ptithcm.thuan6420.yam.ui.component.room.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptithcm.thuan6420.yam.data.dto.User
import com.ptithcm.thuan6420.yam.databinding.FragmentMembersBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.room.MemberItem
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment : BaseFragment() {

    private lateinit var binding: FragmentMembersBinding
    private val viewModel: RoomViewModel by viewModels()
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMembersBinding.inflate(layoutInflater, container, false)
        setRecyclerView()
        fetchMembersData()
        return binding.root
    }

    private fun fetchMembersData() {
        viewModel.getMembers().observe(viewLifecycleOwner) {
            submit(1, it.status, it.message, it.data)
        }
    }

    private fun setRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvMembers.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = groupAdapter
        }
    }

    private fun fetchData(data: Any?) {
        val list = data as List<User>
        groupAdapter.clear()
        list.forEach {
            groupAdapter.add(MemberItem(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.srlMembers.setOnRefreshListener {
            fetchMembersData()
        }
    }

    override fun onError(tasks: Int, message: String?) {
        binding.srlMembers.isRefreshing = false
        binding.pbMembersLoading.visibility = View.GONE
        binding.rvMembers.visibility = View.GONE
        showErrorDialog(message ?: MESSAGE_DEFAULT_ERROR)
    }

    override fun onLoading(tasks: Int) {
        binding.rvMembers.visibility = View.GONE
        binding.pbMembersLoading.visibility = View.VISIBLE
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        binding.srlMembers.isRefreshing = false
        binding.pbMembersLoading.visibility = View.GONE
        fetchData(data)
        binding.rvMembers.visibility = View.VISIBLE
    }
}