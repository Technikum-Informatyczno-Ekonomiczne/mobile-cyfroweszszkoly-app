package com.example.cyfroweszkoly.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleEntry(
    val lessonNumber: Int,
    val time: String,
    val dayOfWeek: String,
    val location: String,
    val className: String)