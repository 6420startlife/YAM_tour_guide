package com.ptithcm.thuan6420.yam.ui.component.station

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.yam.data.dto.Station
import com.ptithcm.thuan6420.yam.data.dto.getAccessToken
import com.ptithcm.thuan6420.yam.data.repository.AuthRepository
import com.ptithcm.thuan6420.yam.data.repository.RoomRepository
import com.ptithcm.thuan6420.yam.data.repository.StationRepository
import com.ptithcm.thuan6420.yam.data.worker.NotificationsWorker
import com.ptithcm.thuan6420.yam.data.worker.RefreshTokenWorker
import com.ptithcm.thuan6420.yam.util.Constants
import com.ptithcm.thuan6420.yam.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class StationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val stationRepository: StationRepository,
    private val roomRepository: RoomRepository,
    private val coroutineContext: CoroutineContext,
    application: Application
) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    internal fun applyWorker() {
        workManager.enqueueUniquePeriodicWork(
            Constants.NOTIFICATIONS_WORKER,
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<NotificationsWorker>(15, TimeUnit.MINUTES)
                .build()
        )
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(Constants.NOTIFICATIONS_WORKER)
    }

    fun getStation() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = stationRepository
                .getStationByIdRoom(
                    authRepository.getToken().getAccessToken(),
                    roomRepository.getRoom().id
                )
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val listStation = body()?.data as List<Station>
                    Log.e(TAG, raw().toString())
                    emit(Resource.success(data = listStation, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: Constants.MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = Constants.MESSAGE_DEFAULT_ERROR))
        }
    }

    fun getStationById() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = stationRepository
                .getStationById(
                    authRepository.getToken().getAccessToken(),
                    stationRepository.getStation().id
                )
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val station = body()?.data as Station
                    Log.e(TAG, raw().toString())
                    setCurrentStation(station)
                    emit(Resource.success(data = station, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: Constants.MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = Constants.MESSAGE_DEFAULT_ERROR))
        }
    }

    fun createStation(name: String, address: String, dateOfStop: String, timeStop: String) =
        liveData(coroutineContext) {
            emit(Resource.loading(data = null))
            try {
                val response = stationRepository
                    .createStation(
                        authRepository.getToken().getAccessToken(),
                        roomRepository.getRoom().id,
                        name, address, dateOfStop, timeStop
                    )
                response.apply {
                    if (isSuccessful && body()?.status == true) {
                        emit(Resource.success(data = null, message = body()?.message))
                    } else {
                        emit(
                            Resource.error(
                                data = null,
                                message = body()?.message ?: Constants.MESSAGE_DEFAULT_ERROR
                            )
                        )
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                emit(Resource.error(data = null, message = Constants.MESSAGE_DEFAULT_ERROR))
            }
        }

    fun updateStation(name: String, address: String, dateOfStop: String, timeStop: String) =
        liveData(coroutineContext) {
            emit(Resource.loading(data = null))
            try {
                val response = stationRepository
                    .updateStation(
                        authRepository.getToken().getAccessToken(),
                        stationRepository.getStation().id,
                        name, address, dateOfStop, timeStop
                    )
                response.apply {
                    if (isSuccessful && body()?.status == true) {
                        emit(Resource.success(data = null, message = body()?.message))
                    } else {
                        emit(
                            Resource.error(
                                data = null,
                                message = body()?.message ?: Constants.MESSAGE_DEFAULT_ERROR
                            )
                        )
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                emit(Resource.error(data = null, message = Constants.MESSAGE_DEFAULT_ERROR))
            }
        }

    fun deleteStation() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = stationRepository
                .deleteStation(
                    authRepository.getToken().getAccessToken(),
                    stationRepository.getStation().id
                )
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = body()?.message ?: Constants.MESSAGE_DEFAULT_ERROR
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = Constants.MESSAGE_DEFAULT_ERROR))
        }
    }

    fun getCurrentStation() = stationRepository.getStation()

    fun setCurrentStation(station: Station) {
        stationRepository.setStation(station)
    }

    fun getNotifications() = stationRepository.getNotifications()

    companion object {
        const val TAG = "StationViewModel"
    }
}