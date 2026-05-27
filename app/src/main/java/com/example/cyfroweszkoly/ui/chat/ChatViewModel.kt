package com.example.cyfroweszkoly.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.BuildConfig
import com.example.cyfroweszkoly.data.model.ChatMessageModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.delay
import com.example.cyfroweszkoly.ui.chat.knowledge.calendarContext

class ChatViewModel: ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction =content { text(PromptBuilder.buildSchoolAssistantPrompt()) }
    )

    //będzie automatycznie pilnował historię czatu
    private val chat = generativeModel.startChat()

    private val _messages = MutableStateFlow<List<ChatMessageModel>>(
        listOf(
            ChatMessageModel(
                text = "Cześć! Jestem asystentem szkoły. " +
                        "W czym mogę Ci dzisiaj pomóc?",
                isFromUser = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessageModel>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)


    fun sendMessage(userQuestion: String) {
        if(userQuestion.isBlank()) return

        // dodajemy wiadomość od użytkownka listy
        val userMessage = ChatMessageModel(text=userQuestion,isFromUser = true)
        _messages.update { currentList -> currentList + userMessage}

        // dodajmey pustą info od asystenta
        val assistantPlaceholder = ChatMessageModel(text="", isFromUser = false)
        _messages.update { currentList -> currentList + assistantPlaceholder }


        viewModelScope.launch {
           try{
               // strumieniujemy dane z modelu
               val responseStream = chat.sendMessageStream(userQuestion)

               // "zbieramy" chunks(kawałek) w miarę napływania
               responseStream.collect{chunk ->
                   val textFragment = chunk.text ?: ""
                  // Rozbijamy paczkę słów na pojedyncze litery!
                   for (char in textFragment) {
                       // Aktualizujemy ostatnią wiadomość litera po literze
                       _messages.update { currentList ->
                           if (currentList.isEmpty()) return@update currentList

                           val updatedList = currentList.toMutableList()
                           val lastIndex = updatedList.lastIndex
                           val lastMessage = updatedList[lastIndex]

                           // Doklejamy tylko JEDNĄ literkę
                           updatedList[lastIndex] = lastMessage.copy(
                               text = lastMessage.text + char
                           )
                           updatedList
                       }

                       // Króciutka pauza (np. 15 milisekund) między literami.
                       // To daje ten piękny, hipnotyzujący efekt pisania na żywo!
                       delay(15)
                   }
               }
           }catch (e: Exception){
               _messages.update { currentList ->

               val updatedList = currentList.toMutableList()
               if(updatedList.isNotEmpty()){
                   updatedList[updatedList.lastIndex] = ChatMessageModel(
                       text = "błąd połączenia: ${e.localizedMessage}",
                       isFromUser = false
                   )
               }
               updatedList
           }} finally {
               // Blok finally wykona się ZAWSZE na samym końcu
               _isLoading.value = false // Ukryj animację ładowania
           }

           }
       }
    }
