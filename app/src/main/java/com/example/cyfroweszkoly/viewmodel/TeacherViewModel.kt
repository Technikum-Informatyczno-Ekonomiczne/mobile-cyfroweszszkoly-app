package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher
import com.example.cyfroweszkoly.repository.TeacherRepository

class TeacherViewModel : ViewModel(){

    // tworzymy połączenie z magazynem (repository)
    private val repository = TeacherRepository()

    // pobieramy dane do pamięci Viewmodelu
    val allTeachers: List<Teacher> = repository.getAllTeachers()


    // Używamy private set, żeby tylko ViewModel mógł zmieniać ten tekst
    var searchQuery by mutableStateOf("")
        private set


    fun updateSearchQuery(query: String){
        searchQuery = query

    }

    // automatyczne filtrwania lista
    val filteredTeachers: List<Teacher>
        get() = if (searchQuery.isBlank()) {
            allTeachers
        } else {
            allTeachers.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.subject.contains(searchQuery, ignoreCase = true)
            }
        }



    fun getTeacherName(teacherId: Int): String{
        return repository.getTeacherById(teacherId)?.name ?: "Nieznany nauczyciel"

    }

    // Zwraca plan lekcji dla danego nauczyciela TYLKO w wybranym dniu
    fun getScheduleForTeacherAndDay(teacherId: Int, dayOfWeek: String): List<ScheduleEntry> {
        val teacher = repository.getTeacherById(teacherId)
        return teacher?.schedule?.filter { it.dayOfWeek == dayOfWeek } ?: emptyList()
    }


}