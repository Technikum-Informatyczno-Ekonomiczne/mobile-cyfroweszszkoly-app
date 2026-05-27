package com.example.cyfroweszkoly.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import com.example.cyfroweszkoly.ui.chat.ChatViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChatMessagesList(
    viewModel: ChatViewModel,
    messages: StateFlow<List<ChatMessageModel>>,
    modifier: Modifier = Modifier
){

  //   Zamieniamy strumień na obserwowalny stan
    val messageList by messages.collectAsState()
    // Tworzymy "pilota" do sterowania naszą listą
    val listState = rememberLazyListState()

    // Reagujemy TYLKO na nową wiadomość (np. kliknięcie "Wyślij")
    // Używamy płynnej animacji
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty()) {
            listState.animateScrollToItem(messageList.size - 1)
        }
    }

    // Reagujemy TYLKO na długość ostatniej wiadomości (strumieniowanie)
    // Używamy BŁYSKAWICZNEGO skoku (bez animacji!), aby nadążyć za literkami
    LaunchedEffect(messageList.lastOrNull()?.text?.length) {
        if (messageList.isNotEmpty()) {
            // brak słowa "animate"
            listState.scrollToItem(messageList.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(messageList) { message ->
            ChatBubble(
                message = message,
                onRetry = { textToRepeat ->
                    // Tutaj wywołujesz tę samą funkcję, co po kliknięciu przycisku "Wyślij"
                    viewModel.sendMessage(textToRepeat)
                }

            )
        }

    }
}

