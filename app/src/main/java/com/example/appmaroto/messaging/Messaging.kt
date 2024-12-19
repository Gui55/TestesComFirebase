package com.example.appmaroto.messaging

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.appmaroto.R
import com.example.appmaroto.model.UserDevice
import com.example.appmaroto.notification.AppMarotoNotification
import com.example.appmaroto.repository.DeviceRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class Messaging: FirebaseMessagingService() {

    private val deviceRepository: DeviceRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("THETOKEN", token)
        CoroutineScope(Dispatchers.IO).launch{
            deviceRepository.registerDevice(UserDevice(token = token), this@Messaging)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        AppMarotoNotification(this).show(message.data)
    }
}