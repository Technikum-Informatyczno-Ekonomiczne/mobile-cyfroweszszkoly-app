package com.example.cyfroweszkoly.ui.alert

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun NotificationPermissionBanner() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current // Pobieramy właściciela cyklu życia

    // Zmienna stanu
    var areNotificationsEnabled by remember {
        mutableStateOf(NotificationManagerCompat.from(context).areNotificationsEnabled())
    }

    // Nasłuchiwanie powrotu do aplikacji
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // Gdy aplikacja wraca na pierwszy plan (np. po zamknięciu ustawień)
            if (event == Lifecycle.Event.ON_RESUME) {
                // Aktualizujemy stan, co wymusi przerysowanie interfejsu
                areNotificationsEnabled =
                    NotificationManagerCompat.from(context).areNotificationsEnabled()
            }
        }

        // Zapisujemy się na subskrypcję zdarzeń
        lifecycleOwner.lifecycle.addObserver(observer)

        // Sprzątamy, gdy komponent znika z ekranu
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Jeśli powiadomienia są włączone, nic nie rysujemy
    if (areNotificationsEnabled) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsOff,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Powiadomienia są wyłączone!",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Aby otrzymywać pilne alerty ze szkoły, musisz włączyć powiadomienia w ustawieniach telefonu.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    // ten kod otwiera dokładnie ekran ustawień powiadomień DLA TEJ APLIKACJI
                    val intent = Intent().apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        } else {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = "package:${context.packageName}".toUri()
                        }
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Napraw to teraz")
            }
        }
    }
}