package com.example.cyfroweszkoly.ui.alert_banner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.AlertModel

@Composable
fun GlobalAlertBanner(alerts: List<AlertModel>) {
    // Jeśli nie ma alertów, ukrywamy cały komponent (nie zajmuje miejsca na ekranie)
    if (alerts.isEmpty()) return

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        alerts.forEach { alert ->
            // Wybór koloru na podstawie flagi isUrgent
            val containerColor = if (alert.isUrgent) {
                MaterialTheme.colorScheme.error // Domyślny czerwony z motywu Material
            } else {
                MaterialTheme.colorScheme.primary // Domyślny główny kolor
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = containerColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = alert.title,
                        color = MaterialTheme.colorScheme.onError, // Biały/kontrastowy tekst
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = alert.message,
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}