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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PaymentInfoCard() {
    // Pobieramy narzędzia systemowe: schowek i kontekst (do wyświetlenia dymku Toast)
    // Nowe API schowka
    val clipboard = LocalClipboard.current
    // Wymagane do uruchomienia asynchronicznej funkcji zapisu
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
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
            // Nagłówek sekcji
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Opłaty za obiady (Czerwiec 2026)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Sekcja z numerem konta i przyciskiem kopiowania
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

                // Magiczny przycisk do kopiowania
                IconButton(
                    onClick = {
                        // Uruchamiamy operację asynchroniczną
                        scope.launch {
                            //Tworzymy obiekt danych schowka Androida
                            val clipData = ClipData.newPlainText("Numer konta SP 311", accountNumber)

                            // 2. Zapisujemy przy użyciu nowej metody setClipEntry
                            clipboard.setClipEntry(ClipEntry(clipData))

                            // 3. Informacja dla użytkownika
                            Toast.makeText(
                                context,
                                "Skopiowano numer konta",
                                Toast.LENGTH_SHORT).show()
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

            HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f))

            // Dodatkowe informacje
            Text(
                text = "Tytuł przelewu: \"OBIAD\", imię i nazwisko, klasa, miesiąc.",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Termin płatności: do 28 maja 2026 r.\nCena (SP 311): 136 zł (I i II danie) / 102 zł (II danie)",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}