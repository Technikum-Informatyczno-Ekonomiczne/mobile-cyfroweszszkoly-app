package com.example.cyfroweszkoly.ui.alert_banner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.AlertModel
import com.example.cyfroweszkoly.viewmodel.AdminManagementViewModel

@Composable
fun AdminAlertListScreen(
    viewModel: AdminManagementViewModel,
    onAddNewClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewClick) {
                Icon(Icons.Default.Add, contentDescription = "Dodaj")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.allAlerts) { alert ->
                AlertAdminItem(
                    alert = alert,
                    onArchive = { viewModel.archiveAlert(alert.id) },
                    onDelete = { viewModel.deleteAlertForever(alert.id) }
                )
            }
        }
    }
}

@Composable
fun AlertAdminItem(
    alert: AlertModel,
    onArchive: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (alert.isActive) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(alert.title, fontWeight = FontWeight.Bold)
                Text(alert.message, style = MaterialTheme.typography.bodySmall)
                if (!alert.isActive) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text("ZAKOŃCZONE") },
                        enabled = false
                    )
                }
            }
            // Przyciski akcji
            if (alert.isActive) {
                // JEŚLI AKTYWNE: Pokaż tylko przycisk kończenia (zielony ptaszek)
                IconButton(onClick = onArchive) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Zakończ",
                        tint = Color(0xFF4CAF50) // Wymuszony zielony
                    )
                }
            } else {
                // JEŚLI ZAKOŃCZONE: Pokaż przycisk permanentnego usunięcia (czerwony kosz)
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Usuń trwale",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}