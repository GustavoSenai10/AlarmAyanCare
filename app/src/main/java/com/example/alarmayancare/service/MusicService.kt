package com.example.alarmayancare.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.view.ContentInfoCompat.Flags
import com.example.alarmayancare.R
class CancelSoundReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Verifique se o MediaPlayer está tocando
        if (Alarm.mediaPlayer?.isPlaying == true) {
            // Pare a reprodução e libero os recursos do MediaPlayer
            Alarm.mediaPlayer?.stop()
            Alarm.mediaPlayer?.release()
            Alarm.mediaPlayer = null
        }
    }
}

class DelayAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        try {
            // Lógica para adiar o alarme em 5 minutos

            // Pausar a reprodução do som
            if (Alarm.mediaPlayer?.isPlaying == true) {
                Alarm.mediaPlayer?.pause()
            }

            // Configurar o alarme para tocar novamente em 5 minutos
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, Alarm::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

            val currentTime = System.currentTimeMillis()
            val delayMillis = 5 * 60 * 1000L // 5 minutos em milissegundos
            val triggerAtMillis = currentTime + delayMillis

            alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, alarmIntent)

            // Usar um Handler para retomar a reprodução após o atraso
            Handler(Looper.getMainLooper()).postDelayed({
                if (Alarm.mediaPlayer != null) {
                    Alarm.mediaPlayer?.start()
                }
            }, delayMillis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


