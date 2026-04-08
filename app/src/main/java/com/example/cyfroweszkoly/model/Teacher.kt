package com.example.cyfroweszkoly.model

import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val id: Int = 0,
    val name: String = "",
    val subject: String = "",
    val schedule: List<ScheduleEntry> = emptyList()
)