package com.ptithcm.thuan6420.yam.ui.base.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.yam.databinding.DialogErrorBinding

class ErrorDialog(
    context: Context?, listener: DialogListener?,
    message: String?
) : AlertDialog(context), View.OnClickListener {
    private lateinit var binding: DialogErrorBinding
    private var listener: DialogListener?
    private var message: String? = null

    init {
        this.listener = listener
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        dismiss()
        val id = v?.id
        if (id == binding.btnError.id) {
            listener?.onClickOnErrorDialog()
        }
    }

    private fun setEvent() {
        binding.tvErrorMessage.text = message
        binding.btnError.setOnClickListener(this)
        window?.setBackgroundDrawable(ColorDrawable(0))
        setCancelable(false)
    }
}