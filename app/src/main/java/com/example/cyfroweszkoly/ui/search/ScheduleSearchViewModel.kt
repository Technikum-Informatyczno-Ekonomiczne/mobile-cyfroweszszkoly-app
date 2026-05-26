package com.example.cyfroweszkoly.ui.search

import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.ScheduleItem
import com.example.cyfroweszkoly.data.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


enum class SearchType {
    TEACHER, // ordinal 0
    ROOM,    // ordinal 1
    CLASS    // ordinal 2
}

class ScheduleSearchViewModel(
    private val repo: ScheduleRepository = ScheduleRepository()
): ViewModel() {

    private val _searchResults = MutableStateFlow<List<ScheduleItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    fun searchSchedule(query: String, type: SearchType){
        repo.getSchedule(query,type){lessons ->
            _searchResults.value = lessons
        }
    }
}