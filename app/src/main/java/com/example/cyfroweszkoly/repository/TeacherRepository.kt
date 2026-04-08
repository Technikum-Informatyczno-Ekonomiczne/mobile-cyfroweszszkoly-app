package com.example.cyfroweszkoly.repository

import android.content.Context
import android.util.Log
import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.serialization.json.Json

class TeacherRepository(context: Context) {


    private val jsonResponse = context.assets
        .open("teachers.json")
        .bufferedReader()
        .use { it.readText() }


    private val allTeachers: List<Teacher> = Json.decodeFromString<List<Teacher>>(jsonResponse)

    fun getAllTeachers(): List<Teacher> = allTeachers

    fun getTeacherById(id: Int): Teacher? {
        return allTeachers.find { it.id == id }
    }

    fun uploadDataToCloud() {

        val db = Firebase.firestore

        allTeachers.forEach { teacher ->

            // Tworzymy "kolekcję" (folder) o nazwie 'teachers'
            // i nazywamy każdy dokument numerem ID nauczyciela
            db.collection("teachers-test")
                .document(teacher.id.toString())
                .set(teacher) // Magia! Firebase sam zamienia obiekt na chmurowy JSON
                .addOnSuccessListener {
                    Log.d("FIREBASE_TEST",
                        "Pomyślnie wysłano: ${teacher.name}")
                }
                .addOnFailureListener { e ->
                    Log.e("FIREBASE_TEST",
                        "Błąd przy wysyłaniu", e)
                }
        }
    }

}