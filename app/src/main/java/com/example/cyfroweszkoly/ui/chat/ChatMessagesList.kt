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
import com.example.cyfroweszkoly.viewmodel.ChatViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChatMessagesList(
    viewModel: ChatViewModel,
    messages: StateFlow<List<ChatMessageModel>>,
    modifier: Modifier = Modifier
){

    // Zamieniamy strumień na obserwowalny stan
    val messageList by messages.collectAsState()
    // Tworzymy "pilota" do sterowania naszą listą
    val listState = rememberLazyListState()

    //Mówimy: "Za każdym razem, gdy zmieni się ROZMIAR listy
    // (messageList.size)..."
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty()) {
            // "...użyj pilota, żeby płynnie zjechać
            // do ostatniego elementu na liście"
            // (indeks ostatniego elementu to rozmiar listy minus 1)
            listState.animateScrollToItem(messageList.size - 1)
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

