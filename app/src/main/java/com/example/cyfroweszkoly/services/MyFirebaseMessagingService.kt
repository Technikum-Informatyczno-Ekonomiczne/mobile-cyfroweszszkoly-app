package com.example.cyfroweszkoly.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Ta funkcja wywołuje się, gdy przychodzi powiadomienie, a apka jest włączona
    // Lub gdy wysyłamy tzw. "Data message"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            showNotification(
                title = it.title ?: "Nowy Alert!",
                message = it.body ?: "")
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "global_alerts"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Kanały powiadomień
        val channel = NotificationChannel(
            channelId,
            "Alerty Szkolne",
            NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Tu potem będzie własna ikonka
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Wypisujemy token w Logcacie.
        // W przyszłości ten token zapiszemy w Firestore obok profilu użytkownika!
        println("Nowy token FCM: $token")
    }
}