package com.ptithcm.thuan6420.yam.ui.component.tasks.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ptithcm.thuan6420.yam.databinding.FragmentSettingsBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorActivity
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorViewModel
import com.ptithcm.thuan6420.yam.ui.component.changepassword.ChangePasswordActivity
import com.ptithcm.thuan6420.yam.ui.component.friends.FriendsActivity
import com.ptithcm.thuan6420.yam.ui.component.profile.ProfileActivity
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: AuthorViewModel by viewModels()
    private val stationViewModel: StationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutLogout.setOnClickListener(this)
        binding.layoutToChangePassword.setOnClickListener(this)
        binding.layoutToProfile.setOnClickListener(this)
        binding.layoutToFriends.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when(v) {
            binding.layoutLogout -> logout()
            binding.layoutToFriends -> navigateToFriends()
            binding.layoutToChangePassword -> navigateToChangePassword()
            binding.layoutToProfile -> navigateToProfile()
        }
    }

    private fun navigateToProfile() {
        startActivity(Intent(this.context, ProfileActivity::class.java))
    }

    private fun navigateToChangePassword() {
        startActivity(Intent(this.context, ChangePasswordActivity::class.java))
    }

    private fun navigateToFriends() {
        startActivity(Intent(this.context, FriendsActivity::class.java))
    }

    private fun logout() {
        viewModel.logout().observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        viewModel.cancelWork()
        stationViewModel.cancelWork()
        this.requireActivity().finishAffinity()
        this.startActivity(Intent(this.context, AuthorActivity::class.java))
    }
}