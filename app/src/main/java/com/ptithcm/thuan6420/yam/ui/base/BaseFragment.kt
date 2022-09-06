package com.ptithcm.thuan6420.yam.ui.base

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ptithcm.thuan6420.yam.ui.base.dialogs.*
import com.ptithcm.thuan6420.yam.util.Constants
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.ptithcm.thuan6420.yam.util.Status
import java.util.*

abstract class BaseFragment : Fragment(), View.OnClickListener, DialogListener {

    private var lastTimeClicked = 0L
    private lateinit var mSuccessDialog: SuccessDialog
    private lateinit var mErrorDialog: ErrorDialog
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mWarningDialog: WarningDialog
    private val defaultFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    fun showWarningDialog(message: String) {
        mWarningDialog = WarningDialog(this.context, this, message)
        mWarningDialog.show()
    }

    fun showProgressDialog() {
        mProgressDialog = ProgressDialog(this.context)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun showSuccessDialog(message: String) {
        mSuccessDialog = SuccessDialog(this.context, this, message)
        mSuccessDialog.show()
    }

    fun showErrorDialog(message: String) {
        mErrorDialog = ErrorDialog(this.context, this, message)
        mErrorDialog.show()
    }

    fun closeKeyBoard() {
        val view = this.requireActivity().currentFocus ?: return
        val inputMethodManager =
            this.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun submit(tasks: Int,status: Status, message: String?, data: Any?) {
        when(status) {
            Status.SUCCESS -> onSuccess(tasks, message, data)
            Status.LOADING -> onLoading(tasks)
            Status.ERROR -> onError(tasks, message)
        }
    }

    fun getDateFromText(text: String): Date = defaultFormat.parse(text)

    open fun onError(tasks: Int, message: String?) {
        hideProgressDialog()
        showErrorDialog(message ?: MESSAGE_DEFAULT_ERROR)
    }

    open fun onLoading(tasks: Int) {
        closeKeyBoard()
        showProgressDialog()
    }

    open fun onSuccess(tasks: Int, message: String?, data: Any?) {
        hideProgressDialog()
        message?.let { showSuccessDialog(it) }
    }

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < Constants.DEFAULT_INTERVAL) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleClick(v)
    }

    override fun onClickOnSuccessDialog() {}
    override fun onClickOnErrorDialog() {}
    override fun onClickOnAccept() {}
    override fun onClickOnDefuse() {}

    open fun onSingleClick(v: View?) {}

}