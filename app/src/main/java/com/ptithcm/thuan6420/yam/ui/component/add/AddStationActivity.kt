package com.ptithcm.thuan6420.yam.ui.component.add

import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isNotEmpty
import com.ptithcm.thuan6420.yam.databinding.ActivityAddStationBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel
import com.ptithcm.thuan6420.yam.util.Constants.CHOOSE_DATE
import com.ptithcm.thuan6420.yam.util.Constants.CHOOSE_TIME

class AddStationActivity : BaseActivity() {
    private lateinit var binding: ActivityAddStationBinding
    private val viewModel: StationViewModel by viewModels()
    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.ibEditTime.setOnClickListener(this)
        binding.ibEditCalendar.setOnClickListener(this)
        binding.btnAddStation.setOnClickListener(this)
    }

    override fun initViewBinding() {
        binding = ActivityAddStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSingleClick(v: View?) {
        when(v) {
            binding.ibEditTime -> getTime(binding.tvTimeStop)
            binding.ibEditCalendar -> getDate(binding.tvDateOfStop)
            binding.btnAddStation -> addStation()
        }
    }

    private fun addStation() {
        if (binding.etStationName.isNotEmpty().not() || binding.etAddress.isNotEmpty().not()) {
            return
        }
        if (binding.tvDateOfStop.text.equals(CHOOSE_DATE)) {
            showErrorDialog("Choose date")
            return
        }
        if (binding.tvTimeStop.text.equals(CHOOSE_TIME)) {
            showErrorDialog("Choose time")
            return
        }
        val name = binding.etStationName.text.toString().trim()
        val date = binding.tvDateOfStop.text.toString()
        val time = binding.tvTimeStop.text.toString()
        val address = binding.etAddress.text.toString().trim()

        viewModel.createStation(name, address, date, time).observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        finish()
    }
}