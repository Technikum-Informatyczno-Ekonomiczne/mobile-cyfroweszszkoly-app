package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    //lista obserwowalna przez Compose
    private val _messages = mutableStateListOf<ChatMessageModel>()
    val messages: List<ChatMessageModel> = _messages

    init{
        _messages.add(ChatMessageModel(
            text="Cześć! Jestem wirtualnym asystem Cyfrowych Szkół. W czym mogę Ci dzisaj pomóc?",
            isUser = false))
    }

    fun sendMessage(userText: String){
        if(userText.isBlank()) return

        _messages.add(ChatMessageModel(
            text=userText,
            isUser =  true)
        )

        // wywołujemy symulację myślenia bota
        viewModelScope.launch {
            delay(1500)
            _messages.add(
                ChatMessageModel(
                    text="Jeszcze nie potrafię połączyć się z serwerem",
                    isUser = false)
            )
        }
    }

}