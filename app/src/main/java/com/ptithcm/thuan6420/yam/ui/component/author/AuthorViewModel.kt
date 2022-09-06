package com.ptithcm.thuan6420.yam.ui.component.author

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.yam.data.dto.Otp
import com.ptithcm.thuan6420.yam.data.dto.Token
import com.ptithcm.thuan6420.yam.data.dto.decodedToken
import com.ptithcm.thuan6420.yam.data.dto.decodedTokenOtp
import com.ptithcm.thuan6420.yam.data.repository.AuthRepository
import com.ptithcm.thuan6420.yam.data.worker.RefreshTokenWorker
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.ptithcm.thuan6420.yam.util.Constants.REFRESH_TOKEN_WORKER
import com.ptithcm.thuan6420.yam.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val coroutineContext: CoroutineContext,
    application: Application
) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    fun isMatchOTP(otp: String) = otp == repository.getOTP()

    internal fun applyWorker() {
        workManager.enqueueUniquePeriodicWork(
            REFRESH_TOKEN_WORKER,
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<RefreshTokenWorker>(15, TimeUnit.MINUTES)
                .build()
        )
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(REFRESH_TOKEN_WORKER)
    }

    fun login(email: String, password: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = repository.login(email, password)
            response.apply {
                if (isSuccessful && (body()?.status == true)) {
                    val token = body()?.data as Token
                    repository.setToken(token)
                    val user = token.decodedToken()
                    repository.setUserFromLocal(user)
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun register(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String
    ) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = repository.register(email, password, fullName, phoneNumber, address)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun sendOtp(email: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = repository.sendOtp(email)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val tokenOtp = body()?.data as Otp
                    val otp = tokenOtp.decodedTokenOtp()
                    repository.setOTP(otp)
                    emit(Resource.success(data = otp, message = null))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun resetPassword(email: String, password: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = repository.resetPassword(email, password)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun logout() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = repository.logout(repository.getToken().refreshToken)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    repository.setToken(token = Token("", ""))
                    emit(Resource.success(data = null, message = body()?.message))
                    Log.e("T64", "Đã logout thành công")
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun isLogin() = repository.getToken().refreshToken.isNotEmpty()

    fun deplay() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        delay(500)
        emit(Resource.success(data = null, message = null))
    }
}