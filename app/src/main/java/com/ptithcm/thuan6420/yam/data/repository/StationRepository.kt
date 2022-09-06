package com.ptithcm.thuan6420.yam.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ptithcm.thuan6420.yam.data.dto.Notification
import com.ptithcm.thuan6420.yam.data.dto.Notifications
import com.ptithcm.thuan6420.yam.data.dto.Station
import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import com.ptithcm.thuan6420.yam.data.remote.ApiHelper
import com.ptithcm.thuan6420.yam.util.Constants
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Inject

class StationRepository @Inject constructor(private val apiHelper: ApiHelper,
                                            private val appSharedPreferences: AppSharedPreferences) {
    suspend fun getStationByIdRoom(authorization: String, roomId: String) =
        apiHelper.getStationByIdRoom(authorization, roomId)

    suspend fun getStationByIdUser(authorization: String, userId: String) =
        apiHelper.getStationByIdUser(authorization, userId)

    suspend fun getStationById(authorization: String, id: Int) =
        apiHelper.getStationById(authorization, id)

    suspend fun createStation(
        authorization: String,
        roomId: String,
        name: String,
        address: String,
        dateOfStop: String,
        timeStop: String
    ) = apiHelper.createStation(authorization, roomId, name, address, dateOfStop, timeStop)

    suspend fun updateStation(
        authorization: String,
        id: Int, name: String,
        address: String,
        dateOfStop: String,
        timeStop: String
    ) = apiHelper.updateStation(authorization, id, name, address, dateOfStop, timeStop)

    suspend fun deleteStation(
        authorization: String,
        id: Int
    ) = apiHelper.deleteStation(authorization, id)

    fun getStation(): Station {
        val jsonStation =
            appSharedPreferences.getSharedPreferencesValue(Constants.STATION)
        return Gson().fromJson(jsonStation, Station::class.java)
    }

    fun setStation(station: Station) {
        val jsonStation = Gson().toJson(station)
        Log.e("T64", jsonStation)
        appSharedPreferences.putSharedPreferencesValue(Constants.STATION, jsonStation)
    }

    fun setNotifications(notifications: Notifications) {
        val jsonNotification = Gson().toJson(notifications)
        Log.e("T64", jsonNotification)
        appSharedPreferences.putSharedPreferencesValue(Constants.NOTIFICATION, jsonNotification)
    }

    fun getNotifications(): Notifications {
        val jsonNotification =
            appSharedPreferences.getSharedPreferencesValue(Constants.NOTIFICATION)
        Log.e("T64", jsonNotification.toString())
        return Gson().fromJson(jsonNotification, Notifications::class.java) ?: Notifications(
            arrayListOf())
    }
}