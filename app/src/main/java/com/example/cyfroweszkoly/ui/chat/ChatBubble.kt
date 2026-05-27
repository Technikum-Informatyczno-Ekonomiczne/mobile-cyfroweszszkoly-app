package com.example.cyfroweszkoly.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.ChatMessageModel
@Composable
fun ChatBubble(
    message: ChatMessageModel,
    onRetry: (String) -> Unit = {}
) {
    // kolor i lokalizacja dymku
    val aligment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (message.isFromUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (message.isFromUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer

    // Zaokrąglenia
    val bubbleShape = if (message.isFromUser) {
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = aligment
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {

            // Przycisk "Powtórz" po lewej stronie dymka ucznia
            if (message.isFromUser) {
                IconButton(
                    onClick = { onRetry(message.text) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Powtórz zapytanie",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }

            // WSPÓLNY KONTENER NA DYMEK - Zawsze rysuje kolor tła i zaokrąglenia
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .clip(bubbleShape)
                    .background(bubbleColor)
                    .padding(12.dp)
            ) {
                // Decydujemy tylko o ZAWARTOŚCI dymka
                if (!message.isFromUser && message.text.isEmpty()) {
                    // Przekazujemy textColor, żeby mruganie miało odpowiedni odcień
                    BlinkingTypingIndicator(color = textColor)
                } else {
                    Text(
                        text = message.text,
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}