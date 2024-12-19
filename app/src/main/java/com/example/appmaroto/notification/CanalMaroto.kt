package com.example.appmaroto.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class CanalMaroto(
    private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun createCanalMaroto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "maroto_channel"
            val name: CharSequence = "Canal Maroto"
            val description = "Um canal maroto."
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description

            notificationManager.createNotificationChannel(channel)

//            // Registre o canal no sistema
//            val notificationManager = context.getSystemService(
//                NotificationManager::class.java
//            )
//            notificationManager.createNotificationChannel(channel)
        }
    }

}