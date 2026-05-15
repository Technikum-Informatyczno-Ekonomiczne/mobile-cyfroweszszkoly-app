package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.repository.AlertRepository
import kotlinx.coroutines.launch

class AdminManagementViewModel : ViewModel() {
    private val repository = AlertRepository()


    fun archiveAlert(alertId: String) {
        viewModelScope.launch {
            try {
                repository.toggleAlertActive(alertId, false)
            } catch (e: Exception) {
                println("Błąd archiwizacji: ${e.message}")
            }
        }
    }

    fun deleteAlertForever(alertId: String) {
        viewModelScope.launch {
            try {
                repository.deleteAlert(alertId)
            } catch (e: Exception) {
                println("Błąd usuwania alertu: ${e.message}")
            }
        }
    }


}