package com.ptithcm.thuan6420.yam.data.dto
import android.util.Log
import com.auth0.android.jwt.JWT
import com.google.gson.annotations.SerializedName


data class Otp(
    @SerializedName("otp")
    val otp: String
)


fun Otp.decodedTokenOtp(): String {
    return try {
        val jwt = JWT(otp)
        val otp = jwt.getClaim("data").asString()
        Log.e("T64", "$otp")
        otp.toString()
    } catch (exception: Exception) {
        ""
    }
}