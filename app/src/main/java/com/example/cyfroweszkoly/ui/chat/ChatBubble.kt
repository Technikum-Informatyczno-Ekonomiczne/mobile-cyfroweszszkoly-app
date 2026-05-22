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
){

    // kolor i lokalizacja dymku
    val aligment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer

    // Zaokrąglenia -
    // dymek użytkownika ma ostry róg po prawej stronie na dole,
    // bot po lewej
    val bubbleShape = if(message.isUser){
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    }else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }


    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = aligment
    ){

        // Używamy Row, aby przycisk i dymek były w jednej linii
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.9f) // Zwiększamy nieco limit, by zmieścić ikonę (90% szerokości)
        ) {

            // Przycisk "Powtórz" pojawia się z lewej strony dymka użytkownika
            if (message.isUser) {
                IconButton(
                    onClick = { onRetry(message.text) },
                    modifier = Modifier.size(36.dp) // Zmniejszony rozmiar, by nie dominował wizualnie
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Powtórz zapytanie",
                        tint = MaterialTheme.colorScheme.outline // Subtelny szary kolor z motywu
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }


            Text(
                text = message.text,
                modifier = Modifier
                    .weight(1f, fill = false) // // fill=false pozwala dymkowi dopasować się do krótkich tekstów, ale zawijać długie
                    .clip(bubbleShape)
                    .background(bubbleColor)
                    .padding(12.dp),
                color = textColor

            )
        }
    }



}