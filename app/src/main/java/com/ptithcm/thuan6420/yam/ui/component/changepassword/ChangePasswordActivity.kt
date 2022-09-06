package com.ptithcm.thuan6420.yam.ui.component.changepassword

import com.ptithcm.thuan6420.yam.databinding.ActivityChangePasswordBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity

class ChangePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun setEvent() {
    }

    override fun initViewBinding() {
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}