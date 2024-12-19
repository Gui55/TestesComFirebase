package com.example.appmaroto.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.toBitmap
import com.example.appmaroto.R
import com.example.appmaroto.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService.NOTIFICATION_SERVICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppMarotoNotification(
    private val context: Context,
) {

    companion object{
        var id = 1
    }

    private val notificationManager: NotificationManager by lazy{
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    fun show(dados: Map<String, String>){
        CoroutineScope(Dispatchers.IO).launch {
            val image = getImgBitmap(dados["image"])
            val style = getStyle(image)
            val notificacao = buildNotification(dados, style)
            notificationManager.notify(id, notificacao)
            id++
        }
    }

    private fun buildNotification(
        dados: Map<String, String>,
        style: NotificationCompat.Style
    ): Notification {
        return NotificationCompat.Builder(context, "maroto_channel")
            .setContentTitle(dados["title"].toString())
            .setContentText(dados["body"].toString())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(style)
            .setContentIntent(createPendingIntent())
            .setAutoCancel(true)
            .build()
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    
    private suspend fun getImgBitmap(imageUrl: String?): Bitmap?{
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        return context.imageLoader.execute(request).image?.toBitmap()
    }

    private suspend fun getStyle(image: Bitmap?): NotificationCompat.Style{
        image?.let{
            return NotificationCompat.BigPictureStyle().bigPicture(it)
        } ?: return NotificationCompat.BigTextStyle()
    }
}