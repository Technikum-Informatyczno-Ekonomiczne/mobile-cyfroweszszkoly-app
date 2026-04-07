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

    fun getScheduleForTeacher(teacherId: Int): List<ScheduleEntry>{
        return repository.getScheduleForTeacher(teacherId)


    }

    fun getTeacherName(teacherId: Int): String{
        return allTeachers.find { it.id == teacherId }?.name ?: "Nieznany nauczyciel"

    }




}