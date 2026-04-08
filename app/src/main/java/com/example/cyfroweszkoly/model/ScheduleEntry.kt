package com.example.cyfroweszkoly.model


import kotlinx.serialization.Serializable



@Serializable
data class ScheduleEntry(
    val lessonNumber: Int = 0,
    val dayOfWeek: String = "",
    val time: String = "",
    val location: String = "",
    val className: String = ""
)