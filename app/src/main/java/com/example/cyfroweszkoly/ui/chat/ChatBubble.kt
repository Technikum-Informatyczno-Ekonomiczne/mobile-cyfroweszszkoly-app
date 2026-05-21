package com.example.cyfroweszkoly.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.ChatMessageModel

@Composable
fun ChatBubble(message: ChatMessageModel){

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
        Text(
            text = message.text,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(12.dp),
            color = textColor

        )
    }



}