package com.example.cyfroweszkoly.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.ScheduleItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


enum class SearchType { TEACHER, ROOM, CLASS }

class ScheduleSearchViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _searchResults = MutableStateFlow<List<ScheduleItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    fun searchSchedule(query: String, type: SearchType){
        if(query.isBlank()) return

        val fieldName = when (type) {
            SearchType.TEACHER -> "teacherName"
            SearchType.ROOM -> "location"
            SearchType.CLASS -> "className"
        }


        db.collection("schedules-mock")
            .get()
            .addOnSuccessListener { documents ->
                val results = documents.map{ doc ->
                    doc.toObject(ScheduleItem::class.java).copy(id = doc.id)

                }
                _searchResults.value =results
            }
            .addOnFailureListener {
                _searchResults.value = emptyList()
            }

    }
}