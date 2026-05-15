package com.example.cyfroweszkoly.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class AlertModel(
    @DocumentId var id: String = "", // Automatycznie zaciągnie ID dokumentu z Firestore
    var title: String = "",
    var message: String = "",
    var isUrgent: Boolean = false,
    var isActive: Boolean = true,
    var timestamp: Date = Date()
)