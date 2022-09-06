package com.ptithcm.thuan6420.yam.ui.component.update

import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isNotEmpty
import com.ptithcm.thuan6420.yam.databinding.ActivityUpdateRoomBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateRoomActivity : BaseActivity() {
    private lateinit var binding: ActivityUpdateRoomBinding
    private val viewModel: RoomViewModel by viewModels()
    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        val room = viewModel.getCurrentItemRoom()
        binding.etRoomNameEdit.setText(room.name)
        binding.etDescriptionEdit.setText(room.description)
        binding.btnUpdateRoom.setOnClickListener(this)
    }

    override fun initViewBinding() {
        binding = ActivityUpdateRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSingleClick(v: View?) {
        when(v) {
            binding.btnUpdateRoom -> updateRoom()
        }
    }

    private fun updateRoom() {
        if (binding.etRoomNameEdit.isNotEmpty().not() ||
            binding.etDescriptionEdit.isNotEmpty().not()) {
            return
        }
        val name = binding.etRoomNameEdit.text.toString()
        val description = binding.etDescriptionEdit.text.toString()
        viewModel.updateRoom(name, description).observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        finish()
    }
}