package com.ptithcm.thuan6420.yam.data.dto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Room(
    @SerializedName("code_join_in")
    val codeJoinIn: String,
    @SerializedName("date_of_create")
    val dateOfCreate: String,
    @SerializedName("decription")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("is_creater")
    val isCreater: Boolean
) : Serializable