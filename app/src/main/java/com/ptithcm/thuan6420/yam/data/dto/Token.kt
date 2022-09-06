package com.ptithcm.thuan6420.yam.data.dto

import com.auth0.android.jwt.JWT
import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)

fun Token.decodedToken(): User {
    return try {
        val jwt = JWT(refreshToken)
        val data = (jwt.getClaim("data").asList(User::class.java))
        User(data[0].id, data[0].fullName, data[0].address, data[0].phoneNumber, data[0].email, "")
    } catch (exception: Exception) {
        User("", "", "", "", "", "")
    }
}

fun Token.getAccessToken(): String = "Bearer $accessToken"
