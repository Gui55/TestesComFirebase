package com.example.appmaroto.api

import com.example.appmaroto.model.UserDevice
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceService {

    @POST("devices")
    suspend fun registerDevice(
        @Body device: UserDevice
    ): Response<Unit>

}