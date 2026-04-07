package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher

class TeacherViewModel : ViewModel(){

    //lokalna, ukryta baza danych
    val  allTeachers = listOf(
        Teacher(1, "Jan Kowalski", "Matematyka"),
        Teacher(2, "Anna Nowak", "Język Polski"),
        Teacher(3, "Marek Ząb", "Fizyka"),
        Teacher(4, "Katarzyna Wójcik", "Matematyka"),
        Teacher(5, "Piotr Wiśniewski", "Informatyka")
    )

    // baza planów lekcji powiązana z ID nauczyciela
    private val schedules = mapOf(
        1 to listOf(
            ScheduleEntry(
                time = "08:00 - 08:45",
                location = "Sala 102 (Liceum)",
                className = "Klasa 1A"),
            ScheduleEntry("08:55 - 09:40",
                "Sala 12 (Podstawówka)", "Klasa 4C")
        ),
        2 to listOf(
            ScheduleEntry("09:50 - 10:35",
                "Sala 204 (Technikum)", "Klasa 3T"),
            ScheduleEntry("10:45 - 11:30",
                "Korytarz (Dyżur)", "DYŻUR")
        )
        // Pozostali na razie nie mają planu
    )

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
        return schedules[teacherId] ?: emptyList()

    }

    fun getTeacherName(teacherId: Int): String{
        return allTeachers.find { it.id == teacherId }?.name ?: "Nieznany nauczyciel"

    }




}