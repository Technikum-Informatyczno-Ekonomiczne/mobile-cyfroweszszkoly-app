package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import com.google.firebase.Firebase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.functions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel: ViewModel() {

    //lista obserwowalna przez Compose
    private val _messages = mutableStateListOf<ChatMessageModel>()
    val messages: List<ChatMessageModel> = _messages

    private val functions: FirebaseFunctions = Firebase.functions("europe-central2")


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
                    _messages.add(ChatMessageModel(answer,false ))

                }else {
                    _messages.add(ChatMessageModel("Otrzymałem pustą odpowiedź z serwera", false))
                }


            }catch (e: Exception){
                e.printStackTrace()
                _messages.add(ChatMessageModel(
                    "Przepraszam, mam problem z połączeniem. Spróbuj ponownie później",
                    false))
            }

        }
    }

}