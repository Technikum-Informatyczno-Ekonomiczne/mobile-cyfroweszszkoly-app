package com.example.cyfroweszkoly.viewmodels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.cyfroweszkoly.data.repository.HistoryRepository
import com.example.cyfroweszkoly.models.HistoryItem

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    private val repository = HistoryRepository(application)


    private val _history = mutableStateOf<List<HistoryItem>>(emptyList())

    val history: State <List<HistoryItem>> = _history

    init {
        loadHistory()
    }

    private fun loadHistory(){
        _history.value = repository
            .loadHistory()
            .sortedBy { it.date }
    }

}