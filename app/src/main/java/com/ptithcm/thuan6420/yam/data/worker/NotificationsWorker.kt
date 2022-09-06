package com.ptithcm.thuan6420.yam.data.worker

import android.app.NotificationManager
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.Notification
import com.ptithcm.thuan6420.yam.data.dto.Notifications
import com.ptithcm.thuan6420.yam.data.dto.getAccessToken
import com.ptithcm.thuan6420.yam.data.repository.AuthRepository
import com.ptithcm.thuan6420.yam.data.repository.StationRepository
import com.ptithcm.thuan6420.yam.util.Constants.CHANNEL_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import kotlin.math.abs

@HiltWorker
class NotificationsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AuthRepository,
    private val stationRepository: StationRepository
) : CoroutineWorker(context, workerParams) {
    private val defaultFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    override suspend fun doWork(): Result {
        return try {
            val response = stationRepository.getStationByIdUser(repository.getToken().getAccessToken(),
                repository.getUserFromLocal().id.toString()
            )
            response.apply {
                if (isSuccessful) {
                    val listStation = body()?.data
                    val notifications = Notifications(arrayListOf())
                    listStation?.forEach {
                        val dateTime = "${it.dateOfStop} ${it.timeStop}"
                        val date = defaultFormat.parse(dateTime)
                        val now = Calendar.getInstance().time
                        val diff = date.time - now.time
                        if (abs(diff) < 1200000L) {
                            val title = "Meet at ${it.name}"
                            val message = "Let's go to ${it.address} at ${it.timeStop} ${it.dateOfStop}"
                            val notification = Notification(title, message)
                            notifications.listNotification.add(notification)
                            Log.e("T64", message)
                            sendNotifications(title, message)
                        }
                    }
                    stationRepository.setNotifications(notifications)
                } else {
                    Log.e("T64", response.raw().toString())
                }
            }
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private fun sendNotifications(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(this.applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_logo)

        val notification = notificationBuilder.build()
        val manager = this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1,notification)
    }
}