package com.ptithcm.thuan6420.yam.data.local

import android.content.Context
import com.ptithcm.thuan6420.yam.util.Constants.APP_SHARED_PREFERENCES
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(val context: Context) {
    fun putSharedPreferencesValue(key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSharedPreferencesValue(key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }
}