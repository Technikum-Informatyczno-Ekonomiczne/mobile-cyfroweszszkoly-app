package com.example.cyfroweszkoly.data.repository

import android.util.Log
import com.example.cyfroweszkoly.data.model.ScheduleItem
import com.example.cyfroweszkoly.data.model.SchoolMetadata
import com.example.cyfroweszkoly.ui.search.SearchType
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ScheduleRepository {

    private val db = Firebase.firestore

    // pobieranie słownika dla wyszukiwarki
    fun getSchoolMetadata(onResult: (SchoolMetadata?)->Unit){
        db.collection("metadata")
            .document("schoolData")
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()){
                    val data = doc.toObject(SchoolMetadata::class.java)
                    onResult(data)
                }else{
                    onResult(null) // dokument nie istnieje
                }
            }
            .addOnFailureListener { ex ->
                Log.e("firebase", "downloading error ", ex)
                onResult(null)
            }
    }

    // metoda do pobierania planu lekcji (filtrowanie
    fun getSchedule(
        query: String,
        type: SearchType,
        onResult: (List<ScheduleItem>) -> Unit
    ){
        if(query.isBlank()){
            onResult(emptyList())
            return
        }

        val fieldName = when (type){
            SearchType.TEACHER -> "teacherName"
            SearchType.ROOM -> "location"
            SearchType.CLASS -> "className"
        }

        db.collection("schedules-mock")
            .whereEqualTo(fieldName, query)
            .get()
            .addOnSuccessListener { documentSnapshots ->
                val results = documentSnapshots.map {doc ->
                    doc.toObject(ScheduleItem::class.java).copy(id = doc.id)

                }
                onResult(results)
            }
    }


}