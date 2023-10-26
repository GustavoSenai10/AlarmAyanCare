package com.example.alarmayancare.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DelayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            if (intent.action == "DELAY_ACTION") {
                // Lógica para adiar o alarme em 5 minutos
                // Isso pode ser feito atualizando o horário do alarme
            }
        }
    }
}

class CancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            if (intent.action == "CANCEL_ACTION") {
                // Lógica para cancelar o alarme
                // Isso pode ser feito cancelando o alarme configurado anteriormente
            }
        }
    }
}