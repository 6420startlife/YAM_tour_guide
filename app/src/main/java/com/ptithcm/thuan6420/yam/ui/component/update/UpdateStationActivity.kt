package com.ptithcm.thuan6420.yam.ui.component.update

import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isNotEmpty
import com.ptithcm.thuan6420.yam.databinding.ActivityUpdateStationBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel

class UpdateStationActivity : BaseActivity() {
    private lateinit var binding: ActivityUpdateStationBinding
    private val viewModel: StationViewModel by viewModels()
    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        val station = viewModel.getCurrentStation()
        binding.etStationName.setText(station.name)
        binding.etAddressEdit.setText(station.address)
        binding.tvDateOfStopEdit.text = station.dateOfStop
        binding.tvTimeStopEdit.text = station.timeStop
        binding.btnUpdateStation.setOnClickListener(this)
        binding.ibEditTime.setOnClickListener(this)
        binding.ibEditCalendar.setOnClickListener(this)
    }

    override fun initViewBinding() {
        binding = ActivityUpdateStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.btnUpdateStation -> updateStation()
            binding.ibEditCalendar -> getDate(binding.tvDateOfStopEdit)
            binding.ibEditTime -> getTime(binding.tvTimeStopEdit)
        }
    }

    private fun updateStation() {
        if (binding.etStationName.isNotEmpty().not() || binding.etAddressEdit.isNotEmpty().not()) {
            return
        }
        val name = binding.etStationName.text.toString().trim()
        val date = binding.tvDateOfStopEdit.text.toString()
        val time = binding.tvTimeStopEdit.text.toString()
        val address = binding.etAddressEdit.text.toString().trim()
        viewModel.updateStation(name, address, date, time).observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        finish()
    }
}