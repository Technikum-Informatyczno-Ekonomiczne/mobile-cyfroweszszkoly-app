package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import com.google.firebase.Firebase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.functions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel: ViewModel() {

    //lista obserwowalna przez Compose
    private val _messages = MutableStateFlow<List<ChatMessageModel>>(
        emptyList()
    )

    val messages: StateFlow<List<ChatMessageModel>> = _messages.asStateFlow()

    private val functions: FirebaseFunctions = Firebase.functions("europe-central2")


    init{
        _messages.value = listOf(
            ChatMessageModel(
                text="Cześć! Jestem wirtualnym asystem Cyfrowych Szkół. " +
                        "W czym mogę Ci dzisiaj pomóc?",
                isUser = false)
        )
    }

    fun sendMessage(userText: String){
        if(userText.isBlank()) return

        // Dodajemy wiadomość użytkownika do strumienia
        // .update pobiera obecną listę (it) i
        // podmienia ją na (staralista + nowyelement)
        _messages.update {
            it + ChatMessageModel(text = userText, isUser = true)
        }

        // wywołujemy symulację myślenia bota
        viewModelScope.launch {
            try{
                // Przygotowujemy dane do wysłania (muszą pasować do 'data.question' w Node.js)
                val requestData = hashMapOf("question" to userText)

                //wywołujemy serwer i czekamy na odpowiedź
                val result = functions
                    .getHttpsCallable("askSchoolAssistant")
                    .call(requestData)
                    .await()

                // wyciągamy odpowiedź z mapy zwróconej przez serwer
                val data = result.data as? Map<*,*>
                val answer = data?.get("answer") as? String

                if(answer != null){
                   // Dodajemy odpowiedź bota do strumienia
                    _messages.update {
                        it + ChatMessageModel(text = answer, isUser = false)
                    }
                } else {
                    _messages.update {
                        it + ChatMessageModel(
                            text = "Otrzymałem pustą odpowiedź z serwera",
                            isUser = false)
                    }
                }


            }catch (e: Exception){
                e.printStackTrace()
                //Dodajemy komunikat o błędzie do strumienia
                _messages.update {
                    it + ChatMessageModel(
                        text = "${e.message}",
                        isUser = false
                    )
                }
            }

        }
    }

}