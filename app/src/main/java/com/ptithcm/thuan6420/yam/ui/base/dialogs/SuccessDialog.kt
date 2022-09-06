package com.ptithcm.thuan6420.yam.ui.base.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.yam.databinding.DialogSuccessBinding

class SuccessDialog(
    context: Context?, listener: DialogListener?,
    message: String?
) : AlertDialog(context), View.OnClickListener {
    private lateinit var binding: DialogSuccessBinding
    private var listener: DialogListener?
    private var message: String?

    init {
        this.message = message
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        dismiss()
        val id = v?.id
        if (id == binding.btnSuccess.id) {
            listener?.onClickOnSuccessDialog()
        }
    }

    private fun setEvent() {
        binding.tvSuccessMessage.text = message
        binding.btnSuccess.setOnClickListener(this)
        window?.setBackgroundDrawable(ColorDrawable(0))
        setCancelable(false)
    }
}