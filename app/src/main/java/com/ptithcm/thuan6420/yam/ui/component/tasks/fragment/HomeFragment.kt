package com.ptithcm.thuan6420.yam.ui.component.tasks.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidCodeJoinIn
import com.ptithcm.thuan6420.yam.data.dto.Room

import com.ptithcm.thuan6420.yam.databinding.FragmentHomeBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.add.AddRoomActivity
import com.ptithcm.thuan6420.yam.ui.component.room.RoomActivity
import com.ptithcm.thuan6420.yam.ui.component.tasks.OnClickRoomItemListener
import com.ptithcm.thuan6420.yam.ui.component.tasks.RoomItem
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), OnClickRoomItemListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: RoomViewModel by viewModels()
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setRecyclerView()
        fetchFoodData()
        return binding.root
    }

    private fun setRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvRoom.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = groupAdapter
        }
    }

    private fun fetchFoodData() {
        viewModel.fetchRoomData().observe(viewLifecycleOwner) {
            submit(1, it.status, it.message, it.data)
        }
    }

    private fun joinInRoom() {
        if (binding.etCodeJoinIn.isValidCodeJoinIn().not()) return
        viewModel.joinInRoom(binding.etCodeJoinIn.text.toString().trim()).observe(this) {
            submit(2, it.status, it.message, it.data)
        }
    }

    override fun onError(tasks: Int, message: String?) {
        when(tasks) {
            1 -> {
                binding.pbHomeLoading.visibility = View.GONE
                binding.srlRoom.isRefreshing = false
                showErrorDialog(message ?: MESSAGE_DEFAULT_ERROR)
            }
            2 -> {
                super.onError(tasks, message)
            }
        }
    }

    override fun onLoading(tasks: Int) {
        when(tasks) {
            1 -> {
                binding.pbHomeLoading.visibility = View.VISIBLE
            }
            2 -> {
                super.onLoading(tasks)
            }
        }
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        when(tasks) {
            1 -> {
                binding.pbHomeLoading.visibility = View.GONE
                binding.srlRoom.isRefreshing = false
                fetchData(data)
            }
            2 -> {
                super.onSuccess(tasks, message, data)
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        fetchFoodData()
    }

    private fun fetchData(data: Any?) {
        val list = data as List<Room>
        groupAdapter.clear()
        list.forEach {
            groupAdapter.add(RoomItem(it, this))
        }
    }

    private fun navigateAddRoom() {
        startActivity(Intent(this.context, AddRoomActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddRoom.setOnClickListener(this)
        binding.btnJoinInRoom.setOnClickListener(this)
        binding.srlRoom.setOnRefreshListener {
            fetchFoodData()
        }
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when (v) {
            binding.btnAddRoom -> navigateAddRoom()
            binding.btnJoinInRoom -> joinInRoom()
        }
    }

    override fun onClickRoomItem(room: Room) {
        viewModel.setCurrentItemRoom(room)
        val intent = Intent(this.context, RoomActivity::class.java)
        startActivity(intent)
    }
}