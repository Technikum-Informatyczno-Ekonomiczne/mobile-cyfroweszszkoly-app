package com.example.cyfroweszkoly.data.model

data class SchoolMetadata(
    val teacherNames: List<String> = emptyList(),
    val roomNames: List<String> = emptyList(),
    val classNames: List<String> = emptyList()
)