package edu.ucb.project.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import edu.ucb.project.DetailActivity
import edu.ucb.project.MainActivity
import edu.ucb.project.R

object NotificationHelper {

    const val CHANNEL_ID = "notificaciones_generales"

    // Nombre del extra acordado con Ari para DetailActivity
    const val EXTRA_MESSAGE_ID = DetailActivity.EXTRA_MESSAGE_ID

    // Crea el canal de notificaciones con importancia alta (cartel en pantalla)
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones generales",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de la aplicacion"
            }
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(canal)
        }
    }

    // Muestra una notificacion local con titulo y contenido.
    // Con messageId (push FCM) abre DetailActivity; sin messageId (notificacion local) vuelve a la pantalla actual.
    fun mostrar(context: Context, titulo: String, contenido: String, messageId: String? = null) {
        crearCanal(context)

        // Id distinto por notificacion: asi no se pisan entre si ni sus PendingIntent
        val idNotificacion = messageId?.hashCode() ?: System.currentTimeMillis().toInt()

        val intent = if (messageId != null) {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE_ID, messageId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        } else {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            context, idNotificacion, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, CHANNEL_ID)
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(context)
        }

        val notificacion = builder
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(titulo)
            .setContentText(contenido)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        context.getSystemService(NotificationManager::class.java)
            .notify(idNotificacion, notificacion)
    }
}