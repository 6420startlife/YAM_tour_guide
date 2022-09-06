package com.ptithcm.thuan6420.yam.data.remote

import com.ptithcm.thuan6420.yam.data.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("email") email: String?, @Field("password") password: String?):
            Response<ResponseMessage<Token>>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("full_name") fullName: String,
        @Field("phone_number") phoneNumber: String,
        @Field("address") address: String):
            Response<ResponseMessage<Any>>

    @FormUrlEncoded
    @POST("auth/otp")
    suspend fun sendOtp(@Field("email") email: String?):
            Response<ResponseMessage<Otp>>

    @FormUrlEncoded
    @POST("auth/reset_password")
    suspend fun resetPassword(
        @Field("email") email: String?,
        @Field("password") password: String?):
            Response<ResponseMessage<Any>>

    @PUT("auth/refreshToken")
    suspend fun refreshToken(@Query("refreshToken") refreshToken: String?):
            Response<ResponseMessage<Token>>

    @DELETE("auth/logout")
    suspend fun logout(@Query("refreshToken") refreshToken: String?):
            Response<ResponseMessage<Any>>

    @GET("room/get_by_id")
    suspend fun getRoomById(
        @Header("Authorization") authorization: String,
        @Query("id_room") roomId: String):
            Response<ResponseMessage<Room>>

    @GET("room")
    suspend fun getRoom(
        @Header("Authorization") authorization: String,
        @Query("id_user") userId: String):
            Response<ResponseMessage<List<Room>>>

    @POST("room")
    suspend fun createRoom(@Header("Authorization") authorization: String,
                           @Query("id_user") userId: String,
                           @Query("name") name: String,
                           @Query("decription") description: String):
            Response<ResponseMessage<Any>>

    @PUT("room")
    suspend fun updateRoom(@Header("Authorization") authorization: String,
                           @Query("id_user") userId: String,
                           @Query("name") name: String,
                           @Query("decription") description: String,
                           @Query("id") roomId: String):
            Response<ResponseMessage<Any>>

    @DELETE("room")
    suspend fun deleteRoom(@Header("Authorization") authorization: String,
                           @Query("id_room") roomId: String):
            Response<ResponseMessage<Any>>

    @POST("room/join_in")
    suspend fun joinInRoom(@Header("Authorization") authorization: String,
                           @Query("id_user") userId: String,
                           @Query("code_join_in") codeJoinIn: String):
            Response<ResponseMessage<Any>>

    @GET("station")
    suspend fun getStationByIdRoom(@Header("Authorization") authorization: String,
                                   @Query("id_room") roomId: String): Response<ResponseMessage<List<Station>>>

    @GET("station/get_by_id")
    suspend fun getStationById(@Header("Authorization") authorization: String,
                                   @Query("id") id: Int): Response<ResponseMessage<Station>>

    @GET("station/get_by_id_user")
    suspend fun getStationByIdUser(@Header("Authorization") authorization: String,
                               @Query("id_user") userId: String): Response<ResponseMessage<List<Station>>>

    @GET("user_in_room")
    suspend fun getUserInRoom(@Header("Authorization") authorization: String,
                              @Query("id_room") roomId: String): Response<ResponseMessage<List<User>>>

    @POST("station")
    suspend fun createStation(@Header("Authorization") authorization: String,
                              @Query("id_room") roomId: String,
                              @Query("name") name: String,
                              @Query("address") address: String,
                              @Query("date_of_stop") dateOfStop: String,
                              @Query("time_stop") timeStop: String): Response<ResponseMessage<Any>>

    @PUT("station")
    suspend fun updateStation(@Header("Authorization") authorization: String,
                              @Query("id") id: Int,
                              @Query("name") name: String,
                              @Query("address") address: String,
                              @Query("date_of_stop") dateOfStop: String,
                              @Query("time_stop") timeStop: String): Response<ResponseMessage<Any>>

    @DELETE("station")
    suspend fun deleteStation(@Header("Authorization") authorization: String,
                              @Query("id") id: Int): Response<ResponseMessage<Any>>

    @GET
    suspend fun getDirectionMap(@Url url: String,
                                @Query("destination") destination: String,
                                @Query("origin") origin: String,
                                @Query("mode") mode: String,
                                @Query("key") key: String): Response<ResponseMap>
}