package com.ptithcm.thuan6420.yam.ui.component.author.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isNotEmptyText
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidEmail
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidPassword
import com.ptithcm.thuan6420.yam.databinding.FragmentForgotPasswordBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorViewModel
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_OTP
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_INVALID_OTP
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_LOADING
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_RETRY
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_SENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: AuthorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun sendOtp() {
        if (binding.etEmailForgotPasswordLayout.isValidEmail().not()) {
            return
        }
        val email = binding.etEmailForgotPassword.text.toString()
        viewModel.sendOtp(email).observe(this) {
            submit(1,it.status, it.message, it.data)
        }
    }

    override fun onLoading(tasks: Int) {
        when(tasks) {
            1 -> {
                super.onLoading(tasks)
                binding.btnSend.text = MESSAGE_LOADING
            }
            2 -> {
                super.onLoading(tasks)
            }
        }
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        when(tasks) {
            1 -> {
                hideProgressDialog()
                binding.btnSend.text = MESSAGE_SENT
            }
            2 -> {
                super.onSuccess(tasks, message, data)
            }
        }
    }

    override fun onError(tasks: Int, message: String?) {
        when(tasks) {
            1 -> {
                super.onError(tasks, message)
                binding.btnSend.text = MESSAGE_RETRY
            }
            2 -> {
                super.onError(tasks, message)
            }
        }
    }

    private fun resetPassword() {
        if (binding.etEmailForgotPasswordLayout.isValidEmail().not()
            || binding.etPasswordForgotPasswordLayout.isValidPassword().not() ||
            binding.etOTPForgotPasswordLayout.isNotEmptyText(MESSAGE_EMPTY_OTP).not()) {
            return
        }
        val otp = binding.etOTPForgotPassword.text.toString()
        if (!viewModel.isMatchOTP(otp)) {
            showErrorDialog(MESSAGE_INVALID_OTP)
            return
        }
        val email = binding.etEmailForgotPassword.text.toString()
        val password = binding.etPasswordForgotPassword.text.toString()
        viewModel.resetPassword(email, password).observe(this) {
            submit(2, it.status, it.message, it.data)
        }
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        navigateToLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutForgotPassword.setOnClickListener(this)
        binding.btnSend.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when(v) {
            binding.layoutForgotPassword -> closeKeyBoard()
            binding.btnBack -> navigateToLogin()
            binding.btnReset -> resetPassword()
            binding.btnSend -> sendOtp()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }
}