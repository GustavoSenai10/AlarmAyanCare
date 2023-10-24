package com.example.alarmayancare


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.TimePicker
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
import java.util.Calendar

@Composable
fun TimePicker(
    selectedTime: MutableState<Calendar>,
    context: Context,
    notificationTitle: String,
    notificationDescription: String,
    onTimeSelected: () -> Unit
) {
    var isTimePickerVisible by remember { mutableStateOf(false) }

    if (isTimePickerVisible) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            selectedTime.value.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.value.set(Calendar.MINUTE, minute)
            onTimeSelected()
        }

        TimePickerDialog(
            context,
            timeSetListener,
            selectedTime.value.get(Calendar.HOUR_OF_DAY),
            selectedTime.value.get(Calendar.MINUTE),
            true
        ).show()
    } else {
        Button(onClick = {
            isTimePickerVisible = true
        }) {
            Text(text = "Selecionar Horário")
        }
    }
}








