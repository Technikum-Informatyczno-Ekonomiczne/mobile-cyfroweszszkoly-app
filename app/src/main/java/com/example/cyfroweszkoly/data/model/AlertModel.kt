package com.example.cyfroweszkoly.data.model

data class AlertModel(
    var id: String = "",
    var title: String = "",
    var message: String = "",
    var isUrgent: Boolean = true,
    var isActive: Boolean = true
)
