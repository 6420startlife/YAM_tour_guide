package com.ptithcm.thuan6420.yam.data.repository

import android.util.Log
import com.google.gson.Gson
import com.ptithcm.thuan6420.yam.data.dto.Room
import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import com.ptithcm.thuan6420.yam.data.remote.ApiHelper
import com.ptithcm.thuan6420.yam.util.Constants
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appSharedPreferences: AppSharedPreferences
) {
    suspend fun getItemRoom(authorization: String, userId: String) =
        apiHelper.getRoom(authorization, userId)

    suspend fun createRoom(
        authorization: String,
        userId: String, name: String,
        description: String
    ) =
        apiHelper.createRoom(authorization, userId, name, description)

    suspend fun updateRoom(
        authorization: String,
        userId: String, name: String,
        description: String,
        roomId: String
    ) =
        apiHelper.updateRoom(authorization, userId, name, description, roomId)

    suspend fun deleteRoom(authorization: String, roomId: String) =
        apiHelper.deleteRoom(authorization, roomId)

    suspend fun joinInRoom(authorization: String, userId: String, codeJoinIn: String) =
        apiHelper.joinInRoom(authorization, userId, codeJoinIn)

    suspend fun getUserInRoom(authorization: String, roomId: String) =
        apiHelper.getUserInRoom(authorization, roomId)

    suspend fun getRoomById(authorization: String, roomId: String) =
        apiHelper.getRoomById(authorization, roomId)

    fun getRoom(): Room {
        val jsonRoom =
            appSharedPreferences.getSharedPreferencesValue(Constants.ROOM)
        return Gson().fromJson(jsonRoom, Room::class.java)
    }

    fun setRoom(room: Room) {
        val jsonRoom = Gson().toJson(room)
        appSharedPreferences.putSharedPreferencesValue(Constants.ROOM, jsonRoom)
    }
}