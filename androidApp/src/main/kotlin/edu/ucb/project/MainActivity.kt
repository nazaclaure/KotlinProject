package edu.ucb.project

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import edu.ucb.project.push.NotificationHelper.EXTRA_MESSAGE_ID

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

        // Solo la primera vez que se crea (no al rotar la pantalla)
        if (savedInstanceState == null) {
            abrirDetalleSiVieneDePush(intent)
        }
    }

    // Si MainActivity ya estaba abierta, el nuevo Intent llega aqui
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        abrirDetalleSiVieneDePush(intent)
    }

    // Push en background: el sistema abre MainActivity con los datos del payload como extras
    private fun abrirDetalleSiVieneDePush(intent: Intent?) {
        val messageId = intent?.getStringExtra(EXTRA_MESSAGE_ID)
            ?: intent?.getStringExtra("google.message_id")
            ?: return
        Log.d(TAG, "Push tocado en background, abriendo detalle: $messageId")
        startActivity(Intent(this, DetailActivity::class.java).putExtra(EXTRA_MESSAGE_ID, messageId))
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