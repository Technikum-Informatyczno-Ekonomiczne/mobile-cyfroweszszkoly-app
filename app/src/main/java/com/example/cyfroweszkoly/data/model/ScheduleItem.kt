package com.example.cyfroweszkoly.data.model

import com.google.firebase.firestore.DocumentId

data class ScheduleItem(
    @DocumentId
    val id: String = "", // Firebase automatycznie uzupełni to pole identyfikatorem dokumentu
    val teacherName: String = "",
    val className: String = "",
    val location: String = "", // Nasza sala (w JSON: location)
    val dayOfWeek: String = "",
    val lessonNumber: Int = 0,
    val time: String = ""
)