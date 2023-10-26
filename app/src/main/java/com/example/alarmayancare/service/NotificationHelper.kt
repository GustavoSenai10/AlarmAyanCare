package com.example.alarmayancare.service


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.alarmayancare.R
import com.google.firebase.messaging.FirebaseMessagingService

object NotificationHelper {

    @SuppressLint("ObsoleteSdkInt")
    fun showNotification(context: Context, title: String, description: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "message_channel"
        val channelId = "message_id"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.baseline_watch_later_24)
            .setAutoCancel(true) // Fechar notificação ao interagir com ela

        manager.notify(1, builder.build())
    }
}

