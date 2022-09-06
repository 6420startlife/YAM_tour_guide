package com.ptithcm.thuan6420.yam.data.dto
import com.google.gson.annotations.SerializedName


data class Station(
    @SerializedName("address")
    val address: String,
    @SerializedName("date_of_stop")
    var dateOfStop: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_room")
    val idRoom: String,
    @SerializedName("is_arrived")
    val isArrived: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("time_stop")
    var timeStop: String
)