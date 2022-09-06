package com.ptithcm.thuan6420.yam.ui.component.add

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isNotEmpty
import com.ptithcm.thuan6420.yam.databinding.ActivityAddRoomBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoomActivity : BaseActivity() {
    private lateinit var binding: ActivityAddRoomBinding
    private val viewModel: RoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun setEvent() {
        binding.btnAddRoom.setOnClickListener(this)
    }

    override fun initViewBinding() {
        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun addRoom() {
        if (binding.etRoomName.isNotEmpty().not() ||
            binding.etDescription.isNotEmpty().not()) {
            return
        }
        val name = binding.etRoomName.text.toString()
        val description = binding.etDescription.text.toString()
        viewModel.addRoom(name, description).observe(this){
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        finish()
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when(v) {
            binding.btnAddRoom -> addRoom()
        }
    }
}