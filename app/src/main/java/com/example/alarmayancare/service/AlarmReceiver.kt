package com.example.alarmayancare.service



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmayancare.service.NotificationHelper.showNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        try {
            // Chame a função que mostra a notificação
            showNotification(context, "oi", "Notificação Ayancare")
        } catch (e: Exception) {
            Log.d("Receive Exception", e.printStackTrace().toString())
        }
    }
}