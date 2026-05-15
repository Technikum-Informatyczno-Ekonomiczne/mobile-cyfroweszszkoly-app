package com.example.cyfroweszkoly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cyfroweszkoly.data.model.AlertModel

@Composable
fun GlobalAlertBanner(alerts: List<AlertModel>) {
    // Jeśli nie ma żadnych aktywnych alertów,
    // komponent nie zajmuje miejsca
    if (alerts.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Odstęp między wieloma bannerami
    ) {
        alerts.forEach { alert ->
            // Dynamiczny dobór kolorów na podstawie priorytetu
            val containerColor = if (alert.isUrgent) {
                MaterialTheme.colorScheme.errorContainer // Jasny czerwony dla pilnych
            } else {
                MaterialTheme.colorScheme.secondaryContainer // Inny odcień dla zwykłych
            }

            val contentColor = if (alert.isUrgent) {
                MaterialTheme.colorScheme.onErrorContainer // Ciemna czerwień dla tekstu
            } else {
                MaterialTheme.colorScheme.onSecondaryContainer
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = alert.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )

                        if (alert.isUrgent) {
                            Text(
                                text = "🚨 PILNE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = alert.message,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}