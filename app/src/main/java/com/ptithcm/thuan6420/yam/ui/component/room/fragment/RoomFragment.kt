package com.ptithcm.thuan6420.yam.ui.component.room.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ptithcm.thuan6420.yam.data.dto.Room
import com.ptithcm.thuan6420.yam.databinding.FragmentRoomBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import com.ptithcm.thuan6420.yam.ui.component.update.UpdateRoomActivity
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DELETE_ROOM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : BaseFragment() {

    private lateinit var binding: FragmentRoomBinding
    private val viewModel: RoomViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditRoom.setOnClickListener(this)
        binding.btnDeleteRoom.setOnClickListener(this)
        binding.srlRoomItem.setOnRefreshListener {
            fetchData()
        }
        binding.btnDeleteRoom.visibility = if (viewModel.getCurrentItemRoom().isCreater)
            View.VISIBLE else View.GONE
        binding.btnEditRoom.visibility = if (viewModel.getCurrentItemRoom().isCreater)
            View.VISIBLE else View.GONE
        fetchData()
    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.btnEditRoom -> navigateToEditRoom()
            binding.btnDeleteRoom -> showWarningDialog(MESSAGE_DELETE_ROOM)
        }
    }

    override fun onClickOnAccept() {
        viewModel.deleteRoom().observe(this) {
            submit(2, it.status, it.message, it.data)
        }
    }

    private fun fetchData() {
        viewModel.getRoomById().observe(viewLifecycleOwner) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onError(tasks: Int, message: String?) {
        when (tasks) {
            1 -> {
                binding.srlRoomItem.isRefreshing = false
                binding.pbRoomLoading.visibility = View.GONE
                binding.layoutRoom.visibility = View.GONE
            }
            2 -> {
                super.onError(tasks, message)
            }
        }
    }

    override fun onLoading(tasks: Int) {
        when (tasks) {
            1 -> {
                binding.pbRoomLoading.visibility = View.VISIBLE
                binding.layoutRoom.visibility = View.GONE
            }
            2 -> {
                super.onLoading(tasks)
            }
        }

    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        when (tasks) {
            1 -> {
                binding.srlRoomItem.isRefreshing = false
                binding.pbRoomLoading.visibility = View.GONE
                binding.layoutRoom.visibility = View.VISIBLE
                val room = data as Room
                binding.tvNameRoom.text = room.name
                binding.tvCodeJoinIn.text = room.codeJoinIn.trim()
                binding.tvDescription.text = room.description
            }
            2 -> {
                super.onSuccess(tasks, message, data)
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        this.requireActivity().finish()
    }

    private fun navigateToEditRoom() {
        startActivity(Intent(this.context, UpdateRoomActivity::class.java))
    }
}