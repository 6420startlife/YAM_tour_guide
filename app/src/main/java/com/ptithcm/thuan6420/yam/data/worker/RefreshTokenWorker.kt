package com.ptithcm.thuan6420.yam.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ptithcm.thuan6420.yam.data.dto.Token
import com.ptithcm.thuan6420.yam.data.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshTokenWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AuthRepository
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val response =
                repository.refreshToken(refreshToken = repository.getToken().refreshToken)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    Log.e(TAG, "Đã refreshToken thành công")
                    val token = body()?.data as Token
                    repository.setToken(token)
                }
            }
            Result.success()
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            Result.failure()
        }
    }
    companion object {
        const val TAG = "Worker"
    }
}