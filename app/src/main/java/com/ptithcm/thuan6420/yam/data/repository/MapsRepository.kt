package com.ptithcm.thuan6420.yam.data.repository

import android.util.Log
import com.google.gson.Gson
import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import com.ptithcm.thuan6420.yam.data.remote.ApiHelper
import com.ptithcm.thuan6420.yam.ui.component.map.Place
import com.ptithcm.thuan6420.yam.util.Constants.ADDRESS
import javax.inject.Inject

class MapsRepository @Inject constructor(private val apiHelper: ApiHelper,
                                         private val appSharedPreferences: AppSharedPreferences) {
    fun setMap(key: String, place: Place) {
        val jsonPlace = Gson().toJson(place)
        Log.e(TAG, jsonPlace)
        appSharedPreferences.putSharedPreferencesValue(key, jsonPlace)
    }

    fun getMap(key: String): Place {
        return Gson().fromJson(appSharedPreferences.getSharedPreferencesValue(key), Place::class.java)
    }

    suspend fun getDirectionMap(url: String,
                                destination: String,
                                origin: String,
                                mode: String,
                                key: String) =
        apiHelper.getDirectionMap(url, destination, origin, mode, key)

    companion object{
        const val TAG = "AddressRepository"
    }
}