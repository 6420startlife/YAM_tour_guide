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
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidPhoneNumber
import com.ptithcm.thuan6420.yam.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseFragment
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorViewModel
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_ADDRESS
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_FULL_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun register() {
        val email = binding.etEmailRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString()
        val fullName = binding.etFullnameRegister.text.toString()
        val phoneNumber = binding.etPhoneNumberRegister.text.toString()
        val address = binding.etAddressRegister.text.toString()

        if (binding.etEmailRegisterLayout.isValidEmail().not() ||
            binding.etPasswordRegisterLayout.isValidPassword().not() ||
            binding.etFullNameRegisterLayout.isNotEmptyText(MESSAGE_EMPTY_FULL_NAME).not() ||
            binding.etPhoneNumberRegisterLayout.isValidPhoneNumber().not() ||
            binding.etAddressRegisterLayout.isNotEmptyText(MESSAGE_EMPTY_ADDRESS).not()) {
            return
        }
        viewModel.register(email, password, fullName, phoneNumber, address).observe(this){
            it.let {
                submit(1, it.status, it.message, it.data)
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        navigateToLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener(this)
        binding.tvToLogin.setOnClickListener(this)
        binding.layoutRegister.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when(v) {
            binding.btnRegister -> register()
            binding.tvToLogin -> navigateToLogin()
            binding.layoutRegister -> closeKeyBoard()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }
}