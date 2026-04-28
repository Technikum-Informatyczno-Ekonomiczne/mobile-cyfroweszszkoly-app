package com.example.cyfroweszkoly.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.ScheduleEntry
import com.example.cyfroweszkoly.data.model.Teacher
import com.example.cyfroweszkoly.repository.TeacherRepository

class TeacherViewModel : ViewModel(){


    private val repository = TeacherRepository()

    // Zmienne stanu dla Compose
    var allTeachers by mutableStateOf<List<Teacher>>(emptyList())
        private set

    var isLoading by mutableStateOf(true) // Stan ładowania
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        // Przy starcie ViewModelu każemy pobrać dane z chmury
        fetchTeachers()
    }

    private fun fetchTeachers() {
        isLoading = true
        repository.getAllTeachersFromCloud { teachersFromDb ->
            // Ten kod wykona się, gdy dane w końcu przylecą z serwera
            allTeachers = teachersFromDb
            isLoading = false
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    // Filtrowanie używa teraz zmiennej stanu `allTeachers`
    val filteredTeachers: List<Teacher>
        get() = if (searchQuery.isBlank()) {
            allTeachers
        } else {
            allTeachers.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.subject.contains(searchQuery, ignoreCase = true)
            }
        }

    fun getTeacherName(teacherId: Int): String {
        return allTeachers.find { it.id == teacherId }?.name ?: "Nieznany nauczyciel"
    }

    fun getScheduleForTeacherAndDay(teacherId: Int, dayOfWeek: String): List<ScheduleEntry> {
        val teacher = allTeachers.find { it.id == teacherId }
        return teacher?.schedule?.filter { it.dayOfWeek == dayOfWeek } ?: emptyList()
    }
}


//    private val repository = TeacherRepository()
//    private val allTeachers = repository.getAllTeachers()
//
//
//
//    // Używamy private set, żeby tylko ViewModel mógł zmieniać ten tekst
//    var searchQuery by mutableStateOf("")
//        private set

    //init {
        // WYWOŁUJEMY MIGRACJĘ
        // (Po udanym wysłaniu usuniemy tę linijkę)
        // repository.uploadDataToCloud()
    //}

//    fun updateSearchQuery(query: String){
//        searchQuery = query
//
//    }

    // automatyczne filtrwania lista
//    val filteredTeachers: List<Teacher>
//        get() = if (searchQuery.isBlank()) {
//            allTeachers
//        } else {
//            allTeachers.filter {
//                it.name.contains(searchQuery, ignoreCase = true) ||
//                it.subject.contains(searchQuery, ignoreCase = true)
//            }
//        }



//    fun getTeacherName(teacherId: Int): String{
//        return repository.getTeacherById(teacherId)?.name ?: "Nieznany nauczyciel"
//
//    }
//
//    // Zwraca plan lekcji dla danego nauczyciela TYLKO w wybranym dniu
//    fun getScheduleForTeacherAndDay(teacherId: Int, dayOfWeek: String): List<ScheduleEntry> {
//        val teacher = repository.getTeacherById(teacherId)
//        return teacher?.schedule?.filter { it.dayOfWeek == dayOfWeek } ?: emptyList()
//    }


//}