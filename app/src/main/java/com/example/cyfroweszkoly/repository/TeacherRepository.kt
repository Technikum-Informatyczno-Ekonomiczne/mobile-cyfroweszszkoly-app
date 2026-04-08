package com.example.cyfroweszkoly.repository

import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher
import kotlinx.serialization.json.Json

class TeacherRepository {

    // Symulujemy odpowiedź z serwera lub pliku w formacie JSON
    private val jsonResponse = """
    [
      {
        "id": 1,
        "name": "Jan Kowalski",
        "subject": "Matematyka",
        "schedule": [
          { 
            "lessonNumber": 1, 
            "dayOfWeek": "Poniedziałek", 
            "time": "08:00 - 08:45", 
            "location": "Sala 102", 
            "className": "Klasa 1A" 
          },
          { 
            "lessonNumber": 2, 
            "dayOfWeek": "Poniedziałek", 
            "time": "08:55 - 09:40", 
            "location": "Sala 12", 
            "className": "Klasa 4C" 
          }
        ]
      }
    ]
    """.trimIndent()


    private val allTeachers: List<Teacher> = Json.decodeFromString<List<Teacher>>(jsonResponse)

    fun getAllTeachers(): List<Teacher> = allTeachers

    fun getTeacherById(id: Int): Teacher? {
        return allTeachers.find { it.id == id }
    }


}