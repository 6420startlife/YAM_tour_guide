package com.ptithcm.thuan6420.yam.data.repository

import android.util.Log
import com.google.gson.Gson
import com.ptithcm.thuan6420.yam.data.dto.Token
import com.ptithcm.thuan6420.yam.data.dto.User
import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import com.ptithcm.thuan6420.yam.data.remote.ApiHelper
import com.ptithcm.thuan6420.yam.util.Constants.OTP
import com.ptithcm.thuan6420.yam.util.Constants.PREF_USER
import com.ptithcm.thuan6420.yam.util.Constants.TOKEN
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appSharedPreferences: AppSharedPreferences
) {
    fun getUserFromLocal(): User {
        val jsonUser =
            appSharedPreferences.getSharedPreferencesValue(PREF_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User?) {
        val jsonUser = Gson().toJson(user)
        Log.e("T64", jsonUser)
        appSharedPreferences.putSharedPreferencesValue(PREF_USER, jsonUser)
    }

    fun getToken(): Token {
        val jsonToken = appSharedPreferences.getSharedPreferencesValue(TOKEN)
        return Gson().fromJson(jsonToken, Token::class.java) ?: Token("","")
    }

    fun setToken(token: Token?) {
        val jsonToken = Gson().toJson(token)
        appSharedPreferences.putSharedPreferencesValue(TOKEN, jsonToken)
    }

    fun getOTP(): String {
        return appSharedPreferences.getSharedPreferencesValue(OTP).toString()
    }

    fun setOTP(otp: String?) {
        appSharedPreferences.putSharedPreferencesValue(OTP, otp)
    }

    suspend fun login(email: String, password: String) = apiHelper.login(email, password)

    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String
    ) = apiHelper.register(email, password, fullName, phoneNumber, address)

    suspend fun sendOtp(email: String) = apiHelper.sendOtp(email)

    suspend fun resetPassword(email: String, password: String) =
        apiHelper.resetPassword(email, password)

    suspend fun refreshToken(refreshToken: String) = apiHelper.refreshToken(refreshToken)

    suspend fun logout(refreshToken: String) = apiHelper.logout(refreshToken)
}