package com.example.alarmayancare



import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import com.example.alarmayancare.ui.theme.AlarmAyanCareTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Alarme() {
    val context = LocalContext.current
    val selectedTime = remember { mutableStateOf(Calendar.getInstance()) }
    var isTimePickerVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

        val  permissionState:PermissionState = rememberPermissionState(
            permission = android.Manifest.permission.POST_NOTIFICATIONS)


            if(!permissionState.status.isGranted){

                Button(onClick = {permissionState.launchPermissionRequest()}) {
                    
                    Text(text = "permissões do alarme")
                }

            }else{

                TimePicker()

            }
        }else{

            Text(
                text = "Por favor aceite a permissões",
                fontSize = 22.sp)
        }
        
    }
}





