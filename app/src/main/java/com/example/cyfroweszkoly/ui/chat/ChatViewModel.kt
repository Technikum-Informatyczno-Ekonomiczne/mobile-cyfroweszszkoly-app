package com.example.cyfroweszkoly.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import com.google.firebase.Firebase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.functions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.collections.plus

class ChatViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    //lista obserwowalna przez Compose
    private val _messages = MutableStateFlow<List<ChatMessageModel>>(
        emptyList()
    )

    val messages: StateFlow<List<ChatMessageModel>> = _messages.asStateFlow()

    private val functions: FirebaseFunctions = Firebase.functions("europe-central2")


    init{
        _messages.value = listOf(
            ChatMessageModel(
                text = "Cześć! Jestem wirtualnym asystem Cyfrowych Szkół. " +
                        "Zadawaj proste i konkretne pytanie na tematy związane z Cyfrowymi Szkołami," +
                        "a na pewno Ci pomogę \uD83D\uDE00",
                isUser = false
            )
        )
    }

    fun sendMessage(userText: String){
        _isLoading.value = true // Pokaż animację ładowania

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
                            isUser = false
                        )
                    }
                }


            }catch (e: Exception){
                e.printStackTrace()
                // Sprawdzamy, skąd pochodzi błąd
                val errorText = if (e is FirebaseFunctionsException) {
                    // Błędy z naszej chmury Firebase (w tym nasze polskie komunikaty HttpsError)
                    e.message
                } else {
                    // Błędy lokalne urządzenia (np. brak WiFi/LTE, problem z siecią)
                    "Brak połączenia z siecią. Sprawdź dostęp do internetu i spróbuj ponownie."
                }

                // Dodajemy wyczyszczony komunikat do strumienia
                _messages.update {
                    it + ChatMessageModel(
                        text = "$errorText",
                        isUser = false
                    )
                }

            } finally {
                _isLoading.value = false // Ukryj animację ładowania
            }

        }
    }

}