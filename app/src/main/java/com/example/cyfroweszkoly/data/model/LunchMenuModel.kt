package com.example.cyfroweszkoly.data.model

data class DailyMenu(
    val day: String,
    val date: String,
    val courses: List<MealCourse>
)

data class MealCourse(
    val category: String,  // np. "Zupa", "II danie"
    val name: String,      // np. "Rosół drobiowy z makaronem"
    val portion: String,   // np. "300ml"
    val allergens: String  // np. "1/3/9" (może być puste)
)