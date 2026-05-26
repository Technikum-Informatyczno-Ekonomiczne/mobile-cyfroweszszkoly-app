package com.example.cyfroweszkoly.ui.search

import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.SchoolMetadata
import com.example.cyfroweszkoly.data.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchAutocompleteViewModel(
    private val repository: ScheduleRepository = ScheduleRepository()
): ViewModel() {

    private var cachedMetadata = SchoolMetadata()

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions = _suggestions.asStateFlow()


    // Zapamiętujemy, co obecnie wpisał użytkownik i czego szuka
    private var currentQuery = ""
    private var currentSearchType = SearchType.TEACHER

    init{

        loadMetadata()
    }

    private fun loadMetadata(){
        println("LOG_FIRESTORE: Rozpoczynam pobieranie słownika przez repozytorium...")

        // Wywołujemy uniwersalną metodę z repozytorium
        repository.getSchoolMetadata { data ->
            if (data != null) {
                println("LOG_FIRESTORE: Słownik pobrany pomyślnie. Nauczycieli: ${data.teacherNames.size}")
                cachedMetadata = data
                refreshSuggestions()
            } else {
                println("LOG_FIRESTORE: Błąd pobierania słownika z repozytorium lub dokument jest pusty.")
            }
        }
    }

    fun onSearchQueryChanged(query: String, type: SearchType) {
        currentQuery = query
        currentSearchType = type
        refreshSuggestions()
    }

    // funkcja wywoływana po każdej zmianie w TextField
    fun refreshSuggestions(){

        val sourceList = when (currentSearchType) {
            SearchType.TEACHER -> cachedMetadata.teacherNames
            SearchType.ROOM -> cachedMetadata.roomNames
            SearchType.CLASS -> cachedMetadata.classNames
        }

        if (currentQuery.isBlank()) {
            // Jeśli nic nie wpisano, pokaż CAŁĄ dostępną listę (np. wszystkich nauczycieli)
            _suggestions.value = sourceList
        } else {
            // Jeśli coś wpisano, odfiltruj odpowiednie pozycje
            _suggestions.value = sourceList.filter {
                it.contains(currentQuery, ignoreCase = true)
            }
        }
    }

}