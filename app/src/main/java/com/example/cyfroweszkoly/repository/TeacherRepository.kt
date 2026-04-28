package com.example.cyfroweszkoly.repository

import android.util.Log
import com.example.cyfroweszkoly.data.model.Teacher
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TeacherRepository() {

    val db = Firebase.firestore

    // private val jsonResponse = context.assets
    //  .open("teachers.json")
    //  .bufferedReader()
    //  .use { it.readText() }

    fun getAllTeachersFromCloud(onResult: (List<Teacher>) -> Unit) {
        db.collection("teachers-test")
            .get() // Pobieramy wszystko z tej kolekcji
            .addOnSuccessListener { result ->
                val downloadedTeachers = mutableListOf<Teacher>()

                for (document in result) {
                    // Firebase  potrafi sam przekształcić
                    // swój format z powrotem na  klasę Kotlina!
                    val teacher = document.toObject(Teacher::class.java)
                    downloadedTeachers.add(teacher)
                }

                Log.d("FIREBASE", "Pobrano nauczycieli: ${downloadedTeachers.size}")
                onResult(downloadedTeachers) // Oddajemy gotową listę do ViewModelu
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE", "Błąd pobierania danych", exception)
                onResult(emptyList()) // W razie braku internetu oddajemy pustą listę
            }
    }
   // private val allTeachers: List<Teacher> = Json.decodeFromString<List<Teacher>>(jsonResponse)

   // fun getAllTeachers(): List<Teacher> = allTeachers

//    fun getTeacherById(id: Int): Teacher? {
//        return allTeachers.find { it.id == id }
//    }


    // Ponieważ internet działa z opóźnieniem,
    // funkcja używa tzw. Callbacka (onResult),
    // który "odda" nam listę nauczycieli dopiero wtedy,
    // gdy ta pobierze się z serwera.
//    fun getAllTeachersFromCloud(onResult: (List<Teacher>) -> Unit) {
//        db.collection("teachers-test")
//            .get() // Pobieramy wszystko z tej kolekcji
//            .addOnSuccessListener { result ->
//                val downloadedTeachers = mutableListOf<Teacher>()
//
//                for (document in result) {
//                    val teacher = document.toObject(Teacher::class.java)
//                    downloadedTeachers.add(teacher)
//                }
//
//                Log.d("FIREBASE", "Pobrano nauczycieli: ${downloadedTeachers.size}")
//                onResult(downloadedTeachers) // Oddajemy gotową listę do ViewModelu
//            }
//            .addOnFailureListener { exception ->
//                Log.e("FIREBASE", "Błąd pobierania danych", exception)
//                onResult(emptyList()) // W razie braku internetu oddajemy pustą listę
//            }
//    }


//    fun uploadDataToCloud() {
//
//
//
//        allTeachers.forEach { teacher ->
//
//            // Tworzymy "kolekcję" (folder) o nazwie 'teachers'
//            // i nazywamy każdy dokument numerem ID nauczyciela
//            db.collection("teachers-test")
//                .document(teacher.id.toString())
//                .set(teacher) // Magia! Firebase sam zamienia obiekt na chmurowy JSON
//                .addOnSuccessListener {
//                    Log.d("FIREBASE_TEST",
//                        "Pomyślnie wysłano: ${teacher.name}")
//                }
//                .addOnFailureListener { e ->
//                    Log.e("FIREBASE_TEST",
//                        "Błąd przy wysyłaniu", e)
//                }
//        }
//    }

}