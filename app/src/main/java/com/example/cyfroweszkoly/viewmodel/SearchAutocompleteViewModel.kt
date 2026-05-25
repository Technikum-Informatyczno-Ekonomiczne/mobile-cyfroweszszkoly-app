package com.example.cyfroweszkoly.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.SchoolMetadata
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchAutocompleteViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()

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
        println("LOG_FIRESTORE: Rozpoczynam pobieranie dokumentu metadata/schoolData...")
        println("ładuję dane")
        db.collection("metadata").document("schoolData")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    println("LOG_FIRESTORE: Dokument istnieje! Surowe dane: ${document.data}")

                    val data = document.toObject(SchoolMetadata::class.java)

                    if (data != null) {
                        println("LOG_FIRESTORE: Deserializacja udana. Nauczycieli: ${data.teacherNames.size}")
                        cachedMetadata = data
                        refreshSuggestions()
                    } else {
                        println("LOG_FIRESTORE: błąd mapowania! Pola w klasie SchoolMetadata mogą nie pasować do bazy.")
                    }
                }else {
                    println("LOG_FIRESTORE: Dokument metadata/schoolData NIE ISTNIEJE w bazie!")
                }

            }
            .addOnFailureListener { e ->
                println("BŁĄD pobierania słownika: ${e.message}")
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