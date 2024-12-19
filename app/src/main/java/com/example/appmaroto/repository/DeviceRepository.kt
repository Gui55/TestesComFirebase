package com.example.appmaroto.repository

import android.content.Context
import com.example.appmaroto.DataStoreConfig
import com.example.appmaroto.api.DeviceService
import com.example.appmaroto.model.UserDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceRepository(
    private val deviceService: DeviceService
) {

    suspend fun registerDevice(
        device: UserDevice,
        context: Context
    ){
        val response = deviceService.registerDevice(device)
        if(response.isSuccessful){
            DataStoreConfig.changeTokenStatus(context, true)
        }
    }

}