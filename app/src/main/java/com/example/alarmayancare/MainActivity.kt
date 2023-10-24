package com.example.alarmayancare


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmayancare.service.AlarmReceiver
import com.example.alarmayancare.service.NotificationHelper
import com.example.alarmayancare.ui.theme.AlarmAyanCareTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmAyanCareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Alarme()
                }
            }
        }
    }
}


@Composable
fun Alarme() {
    val context = LocalContext.current
    val selectedTime = remember { mutableStateOf(Calendar.getInstance()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TimePicker(
            selectedTime = selectedTime,
            context = context,
            notificationTitle = "Título da Notificação",
            notificationDescription = "Descrição da Notificação",
            onTimeSelected = {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                val timeInMillis = selectedTime.value.timeInMillis

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)

                val notificationHelper = NotificationHelper
                notificationHelper.showNotification(context, "Título da Notificação", "Descrição da Notificação")
            }
        )
    }
}

