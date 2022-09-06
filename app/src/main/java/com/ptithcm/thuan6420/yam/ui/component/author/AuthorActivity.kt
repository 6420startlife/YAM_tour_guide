package com.ptithcm.thuan6420.yam.ui.component.author

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.ptithcm.thuan6420.yam.databinding.ActivityAuthorBinding
import com.ptithcm.thuan6420.yam.ui.component.tasks.TasksActivity
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@AndroidEntryPoint
class AuthorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}