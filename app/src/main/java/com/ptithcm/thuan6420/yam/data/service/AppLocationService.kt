package com.ptithcm.thuan6420.yam.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class AppLocationService: Service() {
    private val serviceBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal val service: AppLocationService by lazy {
            this@AppLocationService
        }
    }
    override fun onBind(intent: Intent?): IBinder {
        stopForeground(true)
        return serviceBinder
    }

    override fun onRebind(intent: Intent?) {
        stopForeground(true)
        super.onRebind(intent)
    }

//    override fun onUnbind(intent: Intent?): Boolean {
//        if (requestingLocationUpdates()) {
//            startForeground(NOTIFICATION_ID, notification)
//        }
//        return true
//    }
}