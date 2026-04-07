package com.example.cyfroweszkoly.repository

import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher

class TeacherRepository {


    fun getAllTeachers(): List<Teacher>{
        return listOf(
            Teacher(1, "Jan Kowalski", "Matematyka"),
            Teacher(2, "Anna Nowak", "Język Polski"),
            Teacher(3, "Marek Ząb", "Fizyka"),
            Teacher(4, "Katarzyna Wójcik", "Matematyka"),
            Teacher(5, "Piotr Wiśniewski", "Informatyka")
        )
    }



    fun getScheduleForTeacher(teacherId: Int):List<ScheduleEntry>{
        val schedules = mapOf(
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

        return schedules[teacherId] ?: emptyList()

    }

}