package com.ptithcm.thuan6420.yam.ui.base.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.yam.databinding.DialogYesNoBinding

class WarningDialog(
    context: Context?, listener: DialogListener?,
    message: String?
) : AlertDialog(context), View.OnClickListener {
    private lateinit var binding: DialogYesNoBinding
    private var listener: DialogListener?
    private var message: String?

    init {
        this.message = message
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogYesNoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        dismiss()
        when (v) {
            binding.btnAccept -> {
                listener?.onClickOnAccept()
            }
            binding.btnDefuse -> {
                listener?.onClickOnDefuse()
            }
        }
    }

    private fun setEvent() {
        binding.tvWarningMessage.text = message
        binding.btnAccept.setOnClickListener(this)
        binding.btnDefuse.setOnClickListener(this)
        window?.setBackgroundDrawable(ColorDrawable(0))
        setCancelable(false)
    }
}