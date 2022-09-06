package com.ptithcm.thuan6420.yam.ui.component.author.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidEmail
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidPassword
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.databinding.FragmentLoginBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorViewModel
import com.ptithcm.thuan6420.yam.ui.component.station.StationViewModel
import com.ptithcm.thuan6420.yam.ui.component.tasks.TasksActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthorViewModel by viewModels()
    private val stationViewModel: StationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun login() {
        val email = binding.etEmailLogin.text.toString().trim()
        val password = binding.etPasswordLogin.text.toString()

        if (binding.etEmailLoginLayout.isValidEmail().not() ||
            binding.etPasswordLoginLayout.isValidPassword().not()
        ) {
            return
        }
        viewModel.login(email, password).observe(this) {
            it.let {
                submit(1, it.status, it.message, it.data)
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        viewModel.applyWorker()
        stationViewModel.applyWorker()
        navigateToTasks()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isLogin()) {
            viewModel.deplay().observe(viewLifecycleOwner) {
                submit(2, it.status, it.message, it.data)
            }
        }
        binding.btnLogin.setOnClickListener(this)
        binding.tvToRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        binding.layoutLogin.setOnClickListener(this)
    }

    override fun onError(tasks: Int, message: String?) {
        when (tasks) {
            1 -> super.onError(tasks, message)
            2 -> {
                hideProgressDialog()
            }
        }
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        when (tasks) {
            1 -> super.onSuccess(tasks, message, data)
            2 -> {
                hideProgressDialog()
                navigateToTasks()
            }
        }
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when (v) {
            binding.btnLogin -> login()
            binding.tvToRegister -> navigateToRegister()
            binding.tvForgotPassword -> navigateToForgotPassword()
            binding.layoutLogin -> closeKeyBoard()
        }
    }

    private fun navigateToRegister() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToForgotPassword() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    private fun navigateToTasks() {
        this.requireActivity().finish()
        this.requireActivity().startActivity(Intent(this.context, TasksActivity::class.java))
    }
}