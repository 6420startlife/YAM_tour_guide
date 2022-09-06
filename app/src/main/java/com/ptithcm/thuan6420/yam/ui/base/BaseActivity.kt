package com.ptithcm.thuan6420.yam.ui.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.ptithcm.thuan6420.yam.ui.base.dialogs.*
import com.ptithcm.thuan6420.yam.util.Constants
import com.ptithcm.thuan6420.yam.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, DialogListener {

    private var lastTimeClicked = 0L
    private lateinit var mSuccessDialog: SuccessDialog
    private lateinit var mErrorDialog: ErrorDialog
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mWarningDialog: WarningDialog
    private val calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DATE)
    private var hour = calendar.get(Calendar.HOUR_OF_DAY)
    private var minute = calendar.get(Calendar.MINUTE)
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setEvent()
        observerViewModel()
    }

    open fun observerViewModel() {}

    abstract fun setEvent()

    abstract fun initViewBinding()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showWarningDialog(message: String) {
        mWarningDialog = WarningDialog(this, this, message)
        mWarningDialog.show()
    }

    fun showProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    fun showSuccessDialog(message: String) {
        mSuccessDialog = SuccessDialog(this, this, message)
        mSuccessDialog.show()
    }

    fun showErrorDialog(message: String) {
        mErrorDialog = ErrorDialog(this, this, message)
        mErrorDialog.show()
    }


    fun getTime(textView: AppCompatTextView) {
        val timePickerDialog = TimePickerDialog(this,
            { _, hour, minute ->
                calendar.set(0, 0, 0, hour, minute, 0)
                textView.text = timeFormat.format(calendar.time)
            }, hour, minute, true
        )
        timePickerDialog.show()
    }

    fun getDate(textView: AppCompatTextView) {
        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                textView.text = dateFormat.format(calendar.time)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun closeKeyBoard() {
        val view = this.currentFocus ?: return
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun submit(tasks: Int, status: Status, message: String?, data: Any?) {
        when (status) {
            Status.SUCCESS -> onSuccess(tasks, message, data)
            Status.LOADING -> onLoading(tasks)
            Status.ERROR -> onError(tasks, message)
        }
    }

    open fun onError(tasks: Int, message: String?) {
        hideProgressDialog()
        showErrorDialog(message ?: Constants.MESSAGE_DEFAULT_ERROR)
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