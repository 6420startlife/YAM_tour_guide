package com.ptithcm.thuan6420.yam.data.dto

import com.google.gson.annotations.SerializedName

data class User(var id: String?,
                @SerializedName("full_name")
                var fullName: String?,
                var address: String?,
                @SerializedName("phone_number") var phoneNumber: String?,
                var email: String?,
                var password: String?)
