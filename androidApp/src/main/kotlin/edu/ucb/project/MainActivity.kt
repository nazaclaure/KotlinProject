package edu.ucb.project

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.messaging.FirebaseMessaging
import edu.ucb.project.push.NotificationHelper

class MainActivity : ComponentActivity() {

    // Pide el permiso de notificaciones (obligatorio desde Android 13)
    private val permisoNotificaciones =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
            Log.d(TAG, "Permiso de notificaciones concedido: $concedido")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Log.d("CONFIG_TEST", BuildConfig.GLOBAL_PROVIDER)
        Log.d("CONFIG_TEST", BuildConfig.BASE_URL)
        Log.d("CONFIG_TEST", BuildConfig.APP_NAME)

        NotificationHelper.crearCanal(this)
        pedirPermisoNotificaciones()
        obtenerTokenFcm()

        setContent {
            App()
        }
    }

    private fun pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            permisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Obtiene el token del dispositivo, lo imprime en Logcat y lo copia al portapapeles
    private fun obtenerTokenFcm() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { tarea ->
            if (!tarea.isSuccessful) {
                Log.w(TAG, "No se pudo obtener el token", tarea.exception)
                return@addOnCompleteListener
            }
            val token = tarea.result
            Log.d(TAG, "TOKEN: $token")

            val portapapeles = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            portapapeles.setPrimaryClip(ClipData.newPlainText("fcm_token", token))
            Toast.makeText(this, "Token FCM copiado", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "FCM_TOKEN"
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}