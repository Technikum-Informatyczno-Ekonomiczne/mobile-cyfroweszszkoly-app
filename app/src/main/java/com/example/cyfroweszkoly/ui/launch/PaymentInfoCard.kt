package com.example.cyfroweszkoly.ui.launch

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.viewmodel.PaymentInfoState
import com.example.cyfroweszkoly.viewmodel.PaymentViewModel
import kotlinx.coroutines.launch



@Composable
fun PaymentInfoCard(viewModel: PaymentViewModel) {
    // Narzędzia systemowe
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Obserwowanie stanu z ViewModelu
    val uiState by viewModel.uiState

    // Numer konta zostawiamy statyczny ze względów bezpieczeństwa i wygody kopiowania
    val accountNumber = "37124055981111001075089305"

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //  NAGŁÓWEK SEKCI
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Opłaty za obiady",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            //  BLOK Z NUMEREM KONTA I KOPIOWANIEM
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Szkoła Podstawowa nr 311",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = accountNumber,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            val clipData = ClipData.newPlainText("Numer konta SP 311", accountNumber)
                            clipboard.setClipEntry(ClipEntry(clipData))
                            Toast.makeText(context, "Skopiowano numer konta", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Kopiuj numer konta",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // DYNAMICZNA TREŚĆ Z WORDPRESSA
            when (val state = uiState) {
                is PaymentInfoState.Loading -> {
                    // Animacja ładowania
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is PaymentInfoState.Success -> {
                    // Wyczyszczony tekst wprost z API
                    Text(
                        text = state.contentText,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }

                is PaymentInfoState.Error -> {
                    // Obsługa błędu sieciowego
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}








