package com.ptithcm.thuan6420.yam.ui.component.room.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptithcm.thuan6420.yam.data.dto.Station
import com.ptithcm.thuan6420.yam.databinding.FragmentStationBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.add.AddStationActivity
import com.ptithcm.thuan6420.yam.ui.component.map.MapsActivity
import com.ptithcm.thuan6420.yam.ui.component.room.OnClickStationItemListener
import com.ptithcm.thuan6420.yam.ui.component.room.RoomViewModel
import com.ptithcm.thuan6420.yam.ui.component.room.StationItem
import com.ptithcm.thuan6420.yam.ui.component.station.StationActivity
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel
import com.ptithcm.thuan6420.yam.util.Constants.DESTINATION
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationFragment : BaseFragment(), OnClickStationItemListener {
    private lateinit var binding: FragmentStationBinding
    private val viewModel: StationViewModel by viewModels()
    private val tasksViewModel: RoomViewModel by viewModels()
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationBinding.inflate(layoutInflater, container, false)
        setRecyclerView()
        fetchStationData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddStation.visibility = if (tasksViewModel.getCurrentItemRoom().isCreater)
            View.VISIBLE else View.GONE
        binding.srlStation.setOnRefreshListener {
            fetchStationData()
        }
        binding.btnAddStation.setOnClickListener(this)
    }

    private fun fetchStationData() {
        viewModel.getStation().observe(viewLifecycleOwner) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onError(tasks: Int, message: String?) {
        binding.srlStation.isRefreshing = false
        binding.rvStation.visibility = View.GONE
        binding.pbStationLoading.visibility = View.GONE
        showErrorDialog(message ?: MESSAGE_DEFAULT_ERROR)
    }

    override fun onLoading(tasks: Int) {
        binding.rvStation.visibility = View.GONE
        binding.pbStationLoading.visibility = View.VISIBLE
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        binding.srlStation.isRefreshing = false
        binding.rvStation.visibility = View.VISIBLE
        binding.pbStationLoading.visibility = View.GONE
        fetchData(data)
    }

    private fun setRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvStation.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = groupAdapter
        }
    }

    private fun fetchData(data: Any?) {
        val list = data as List<Station>
        groupAdapter.clear()
        list.forEach {
            groupAdapter.add(StationItem(it, this))
        }
    }

    override fun onClickStationItem(station: Station) {
        viewModel.setCurrentStation(station)
        startActivity(Intent(this.context, StationActivity::class.java))
    }

    override fun onClickToLocation(address: String) {
        val intent = Intent(this.context, MapsActivity::class.java)
        val bundle = Bundle()
        bundle.putString(DESTINATION, address)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.btnAddStation -> navigateToAddStation()
        }
    }

    private fun navigateToAddStation() {
        startActivity(Intent(this.context, AddStationActivity::class.java))
    }
}