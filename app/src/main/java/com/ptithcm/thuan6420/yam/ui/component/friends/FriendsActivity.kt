package com.ptithcm.thuan6420.yam.ui.component.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ptithcm.thuan6420.yam.databinding.ActivityFriendsBinding
import com.ptithcm.thuan6420.yam.databinding.ActivityProfileBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity

class FriendsActivity : BaseActivity() {
    private lateinit var binding: ActivityFriendsBinding
    override fun setEvent() {
    }

    override fun initViewBinding() {
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}