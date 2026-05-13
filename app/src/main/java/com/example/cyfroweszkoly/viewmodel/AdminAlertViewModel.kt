package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.repository.AlertRepository
import kotlinx.coroutines.launch

class AdminAlertViewModel: ViewModel() {
    private val repository = AlertRepository()

    var title by mutableStateOf("")
    var message by mutableStateOf("")
    var isUrgent by mutableStateOf(false)

    // flaga blokująca wysłanie
    var isSending by mutableStateOf(false)

    fun sendAlert(onSuccess: ()->Unit){
        //gdy formularz pusty
        if (title.isBlank() || message.isBlank()) return

        viewModelScope.launch{
            isSending=true
            try{
                repository.addAlert(title, message, isUrgent)
                title=""
                message=""
                isUrgent=false
                onSuccess()

            }catch (e: Exception){
                println("Błąd wysyłania alertu: ${e.message}")
            }finally {
                isSending=false
            }
        }
    }

}