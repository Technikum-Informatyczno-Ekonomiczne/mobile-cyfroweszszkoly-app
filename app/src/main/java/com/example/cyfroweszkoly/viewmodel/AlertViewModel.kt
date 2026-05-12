package com.example.cyfroweszkoly.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.AlertModel
import com.example.cyfroweszkoly.repository.AlertRepository
import kotlinx.coroutines.launch

class AlertViewModel : ViewModel() {
    private val repository = AlertRepository()

    // Stan, który będzie obserwował interfejs (UI)
    var activeAlerts by mutableStateOf<List<AlertModel>>(emptyList())
        private set

    init {
        // Startujemy nasłuchiwanie od razu po stworzeniu ViewModelu
        viewModelScope.launch {
            repository.getActiveAlerts().collect { alertsFromDb ->
                // Za każdym razem, gdy admin coś doda/zmieni w Firebase,
                // ta linijka wykona się SAMA, aktualizując listę na ekranie!
                activeAlerts = alertsFromDb
            }
        }
    }
}
