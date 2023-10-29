package com.example.alarmayancare


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.alarmayancare.service.Alarm
import java.text.SimpleDateFormat
import java.util.Calendar


@Composable
fun TimePicker() {
    val context = LocalContext.current
    val selectedTime = remember { mutableStateOf(Calendar.getInstance()) }
    val currentTime = selectedTime.value
    val hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)
    var isTimePickerVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Horário Selecionado: ${formatTime(currentTime)}")

        Button(onClick = {
            isTimePickerVisible = true
        }) {
            Text(text = "Selecionar Horário")
        }

        if (isTimePickerVisible) {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
                selectedTime.value.set(Calendar.HOUR_OF_DAY, hour)
                selectedTime.value.set(Calendar.MINUTE, min)
                isTimePickerVisible = false

                // Comparar o horário atual com o horário selecionado.
                val currentCalendar = Calendar.getInstance()
                if (selectedTime.value.after(currentCalendar)) {
                    // O horário selecionado é no futuro.
                    // Calcular o atraso até o horário selecionado.
                    val delayMillis = selectedTime.value.timeInMillis - currentCalendar.timeInMillis

                    // Usar um Handler para agendar a notificação após o atraso.
                    Handler(Looper.getMainLooper()).postDelayed({
                        configureNotification(context)
                    }, delayMillis)
                }
            }

            // Mostrar o TimePickerDialog para selecionar o horário.
            TimePickerDialog(
                context,
                timeSetListener,
                hourOfDay,
                minute,
                true
            ).show()
        }
    }
}


private fun formatTime(calendar: Calendar): String {
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(calendar.time)
}

private fun configureNotification(context: Context) {
    val timeSec = System.currentTimeMillis()
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, Alarm::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.set(AlarmManager.RTC_WAKEUP,timeSec,pendingIntent)

}
