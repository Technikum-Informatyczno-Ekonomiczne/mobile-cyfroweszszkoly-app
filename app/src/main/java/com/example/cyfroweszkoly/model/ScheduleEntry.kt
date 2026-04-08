package com.example.cyfroweszkoly.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleEntry(
    val time: String,
    val dayOfWeek: String,
    val location: String,
    val className: String)