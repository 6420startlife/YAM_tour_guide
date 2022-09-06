package com.ptithcm.thuan6420.yam.ui.component.profile

import com.ptithcm.thuan6420.yam.databinding.ActivityProfileBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun setEvent() {
    }

    override fun initViewBinding() {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}