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
    val hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    var isTimePickerVisible by remember { mutableStateOf(false) }

    if (isTimePickerVisible) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            selectedTime.value.set(Calendar.HOUR_OF_DAY, hour)
            selectedTime.value.set(Calendar.MINUTE, min)
            isTimePickerVisible = false

            // Agora, vamos comparar o horário atual com o horário selecionado.
            val currentCalendar = Calendar.getInstance()
            if (selectedTime.value.after(currentCalendar)) {
                // O horário selecionado é no futuro, então configuramos o alarme e notificação.
                configureAlarmAndNotification(context, selectedTime.value)
            }
        }

        TimePickerDialog(
            context,
            timeSetListener,
            hourOfDay,
            minute,
            true
        ).show()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Horário Selecionado: ${formatTime(currentTime)}")

        Button(onClick = {
            isTimePickerVisible = true
        }) {
            Text(text = "Selecionar Horário")
        }
    }
}

private fun formatTime(calendar: Calendar): String {
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(calendar.time)
}

private fun configureAlarmAndNotification(context: Context, selectedTime: Calendar) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val timeInMillis = selectedTime.timeInMillis

    alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)

    val notificationHelper = NotificationHelper
    notificationHelper.showNotification(context, "Título da Notificação", "Descrição da Notificação")
}
