package com.example.cyfroweszkoly.repository

import android.content.Context
import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.model.Teacher
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


}