package com.ptithcm.thuan6420.yam.ui.component.station

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.yam.data.dto.Station
import com.ptithcm.thuan6420.yam.databinding.ActivityStationBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import com.ptithcm.thuan6420.yam.ui.component.update.UpdateStationActivity
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DELETE_STATION

class StationActivity : BaseActivity() {

    private lateinit var binding: ActivityStationBinding
    private val viewModel: StationViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()

    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.btnDeleteStation.visibility =
            if (roomViewModel.getCurrentItemRoom().isCreater) View.VISIBLE else View.GONE
        binding.btnEditStation.visibility =
            if (roomViewModel.getCurrentItemRoom().isCreater) View.VISIBLE else View.GONE
        binding.srlStationItem.setOnRefreshListener {
            fetchData()
        }
        binding.btnEditStation.setOnClickListener(this)
        binding.btnDeleteStation.setOnClickListener(this)
        fetchData()
    }

    private fun fetchData() {
        viewModel.getStationById().observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onError(tasks: Int, message: String?) {
        when (tasks) {
            1 -> {
                binding.srlStationItem.isRefreshing = false
                binding.pbStationLoading.visibility = View.GONE
                binding.layoutStation.visibility = View.GONE
            }
            2 -> {
                super.onError(tasks, message)
            }
        }

    }

    override fun onLoading(tasks: Int) {
        when (tasks) {
            1 -> {
                binding.pbStationLoading.visibility = View.VISIBLE
                binding.layoutStation.visibility = View.GONE
            }
            2 -> {
                super.onLoading(tasks)
            }
        }

    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        when (tasks) {
            1 -> {
                binding.srlStationItem.isRefreshing = false
                binding.pbStationLoading.visibility = View.GONE
                binding.layoutStation.visibility = View.VISIBLE
                val station = data as Station
                binding.tvNameStation.text = station.name
                binding.tvDateOfStopStation.text = station.dateOfStop
                binding.tvStatus.text = if (station.isArrived) "Arrived" else "None"
                binding.tvTimeStop.text = station.timeStop
                binding.tvAddress.text = station.address
            }
            2 -> {
                super.onSuccess(tasks, message, data)
            }
        }
    }

    override fun initViewBinding() {
        binding = ActivityStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.btnEditStation -> navigateToEditStation()
            binding.btnDeleteStation -> showWarningDialog(MESSAGE_DELETE_STATION)
        }
    }

    override fun onClickOnAccept() {
        viewModel.deleteStation().observe(this) {
            submit(2, it.status, it.message, it.data)
        }
    }

    private fun navigateToEditStation() {
        startActivity(Intent(this@StationActivity, UpdateStationActivity::class.java))
    }

    override fun onClickOnSuccessDialog() {
        finish()
    }
}