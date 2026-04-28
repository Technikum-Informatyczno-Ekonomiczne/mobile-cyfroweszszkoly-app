package com.example.cyfroweszkoly.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

// Główny model danych
data class Achievement(
    @DocumentId val id: String = "",
    val title: String = "",
    val date: Timestamp = Timestamp.now(),
    val schoolType: String = "",
    val tags: List<String> = emptyList(),
    val description: String = "",
    val imageUrls: List<String> = emptyList(),
    val details: List<AchievementDetail> = emptyList()
)

// Model dla elastycznej listy wyników
data class AchievementDetail(
    val subtitle: String = "",
    val content: String = ""
)
