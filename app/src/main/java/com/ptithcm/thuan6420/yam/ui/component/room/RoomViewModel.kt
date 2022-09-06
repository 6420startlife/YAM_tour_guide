package com.ptithcm.thuan6420.yam.ui.component.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ptithcm.thuan6420.yam.data.dto.Room
import com.ptithcm.thuan6420.yam.data.dto.User
import com.ptithcm.thuan6420.yam.data.dto.getAccessToken
import com.ptithcm.thuan6420.yam.data.repository.AuthRepository
import com.ptithcm.thuan6420.yam.data.repository.RoomRepository
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.ptithcm.thuan6420.yam.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val authRepository: AuthRepository,
    private val coroutineContext: CoroutineContext
): ViewModel() {

    fun fetchRoomData() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.getItemRoom(
                authRepository.getToken().getAccessToken(),
                authRepository.getUserFromLocal().id.toString()
            )
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val data = body()?.data as List<Room>
                    emit(Resource.success(data = data, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun getRoomById() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.getRoomById(
                authRepository.getToken().getAccessToken(),
                roomRepository.getRoom().id
            )
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val data = body()?.data as Room
                    emit(Resource.success(data = data, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun addRoom(name: String, description: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.createRoom(authRepository.getToken().getAccessToken(),
                authRepository.getUserFromLocal().id.toString(), name, description)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun joinInRoom(codeJoinIn: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.joinInRoom(authRepository.getToken().getAccessToken(),
                authRepository.getUserFromLocal().id.toString(), codeJoinIn)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun getMembers() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository
                .getUserInRoom(authRepository.getToken().getAccessToken(),
                    roomRepository.getRoom().id)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    val listStation = body()?.data as List<User>
                    emit(Resource.success(data = listStation, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun updateRoom(name: String, description: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.updateRoom(authRepository.getToken().getAccessToken(),
                authRepository.getUserFromLocal().id.toString(), name, description, getCurrentItemRoom().id)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun deleteRoom() = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = roomRepository.deleteRoom(authRepository.getToken().getAccessToken(),
                getCurrentItemRoom().id)
            response.apply {
                if (isSuccessful && body()?.status == true) {
                    emit(Resource.success(data = null, message = body()?.message))
                } else {
                    emit(Resource.error(data = null, message = body()?.message ?: MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun setCurrentItemRoom(room: Room) {
        roomRepository.setRoom(room)
    }

    fun getCurrentItemRoom() = roomRepository.getRoom()

    companion object{
        const val TAG = "RoomViewModel"
    }
}