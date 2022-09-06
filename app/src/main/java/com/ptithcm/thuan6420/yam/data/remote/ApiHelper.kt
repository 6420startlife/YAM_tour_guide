package com.ptithcm.thuan6420.yam.data.remote

import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun login(email: String, password: String) =
        apiService.login(email, password)

    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String
    ) =
        apiService.register(email, password, fullName, phoneNumber, address)

    suspend fun sendOtp(email: String) =
        apiService.sendOtp(email)

    suspend fun resetPassword(email: String, password: String) =
        apiService.resetPassword(email, password)

    suspend fun refreshToken(refreshToken: String) =
        apiService.refreshToken(refreshToken)

    suspend fun logout(refreshToken: String) =
        apiService.logout(refreshToken)

    suspend fun getRoom(authorization: String, userId: String) =
        apiService.getRoom(authorization, userId)

    suspend fun createRoom(
        authorization: String,
        userId: String, name: String,
        description: String
    ) =
        apiService.createRoom(authorization, userId, name, description)

    suspend fun updateRoom(
        authorization: String,
        userId: String, name: String,
        description: String,
        roomId: String
    ) =
        apiService.updateRoom(authorization, userId, name, description, roomId)

    suspend fun deleteRoom(authorization: String, roomId: String) =
        apiService.deleteRoom(authorization, roomId)

    suspend fun joinInRoom(authorization: String, userId: String, codeJoinIn: String) =
        apiService.joinInRoom(authorization, userId, codeJoinIn)

    suspend fun getStationByIdRoom(authorization: String, roomId: String) =
        apiService.getStationByIdRoom(authorization, roomId)

    suspend fun getUserInRoom(authorization: String, roomId: String) =
        apiService.getUserInRoom(authorization, roomId)

    suspend fun getRoomById(authorization: String, roomId: String) =
        apiService.getRoomById(authorization, roomId)

    suspend fun getStationById(authorization: String, id: Int) =
        apiService.getStationById(authorization, id)

    suspend fun createStation(
        authorization: String,
        roomId: String,
        name: String,
        address: String,
        dateOfStop: String,
        timeStop: String) =
        apiService.createStation(authorization, roomId, name, address, dateOfStop, timeStop)

    suspend fun updateStation(
        authorization: String,
        id: Int, name: String,
        address: String,
        dateOfStop: String,
        timeStop: String) =
        apiService.updateStation(authorization, id, name, address, dateOfStop, timeStop)

    suspend fun deleteStation(
        authorization: String,
        id: Int) =
        apiService.deleteStation(authorization, id)

    suspend fun getDirectionMap(url: String,
                                destination: String,
                                origin: String,
                                mode: String,
                                key: String) = apiService.getDirectionMap(url, destination, origin, mode, key)

    suspend fun getStationByIdUser(authorization: String, userId: String) = apiService.getStationByIdUser(authorization, userId)
}