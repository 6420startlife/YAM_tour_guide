package com.ptithcm.thuan6420.basecleanarchitecture.ui.base

import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_EMAIL
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_PASSWORD
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_EMPTY_PHONE_NUMBER
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_INVALID_EMAIL
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_INVALID_PASSWORD
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_INVALID_PHONE_NUMBER
import com.ptithcm.thuan6420.yam.util.Constants.REGEX_CODE_JOIN_IN
import com.ptithcm.thuan6420.yam.util.Constants.REGEX_PHONE_NUMBER
import java.util.regex.Pattern

fun TextInputLayout.isValidEmail(): Boolean {
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_EMAIL
        return false
    }
    val isValid = Patterns.EMAIL_ADDRESS.matcher(editText?.text.toString().trim()).matches()
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_EMAIL
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isValidPassword(): Boolean {
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_PASSWORD
        return false
    }
    val isValid = (editText?.text?.length ?: 0) >= 6
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_PASSWORD
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isValidPhoneNumber(): Boolean {
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_PHONE_NUMBER
        return false
    }
    val isValid = Pattern.compile(REGEX_PHONE_NUMBER)
        .matcher(editText?.text.toString().trim()).matches()
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_PHONE_NUMBER
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isNotEmptyText(message: String): Boolean {
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = message
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun AppCompatEditText.isNotEmpty(): Boolean {
    if (text?.isEmpty() != false) {
        error = "Không được bỏ trống trường này"
        return false
    }
    error = null
    return true
}

fun AppCompatEditText.isValidCodeJoinIn(): Boolean {
    if (text?.isEmpty() != false) {
        error = "Không được bỏ trống trường này"
        return false
    }
    val isValid = Pattern.compile(REGEX_CODE_JOIN_IN)
        .matcher(text ?: "").matches()
    if (isValid.not()) {
        error = "Code phải có nhiều hơn 6 ký tự và in hoa"
        return false
    }
    error = null
    return true
}



