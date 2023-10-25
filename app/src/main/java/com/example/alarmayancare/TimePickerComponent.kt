package com.example.alarmayancare


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.alarmayancare.service.AlarmReceiver
import com.example.alarmayancare.service.NotificationHelper
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun TimePicker(
    selectedTime: MutableState<Calendar>,
    context: Context
) {
    val currentTime = selectedTime.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Horário Selecionado: ${formatTime(currentTime)}")

        Button(onClick = {

            // Configurar o alarme e notificação para o horário selecionado.
            configureAlarmAndNotification(context, selectedTime.value)
        }) {
            Text(text = "Selecionar Horário")
        }
    }
}

// Função para formatar o horário no formato "HH:mm"
private fun formatTime(calendar: Calendar): String {
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(calendar.time)
}

// Função para configurar o alarme e notificação
private fun configureAlarmAndNotification(context: Context, selectedTime: Calendar) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val timeInMillis = selectedTime.timeInMillis

    // Configurar o alarme para o horário selecionado
    alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)

    // Mostrar a notificação com um título e descrição
    val notificationHelper = NotificationHelper
    notificationHelper.showNotification(context, "Título da Notificação", "Descrição da Notificação")
}

