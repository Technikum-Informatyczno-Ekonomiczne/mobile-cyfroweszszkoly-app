package com.example.cyfroweszkoly.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class AlertRepository {
    private val db by lazy { Firebase.firestore }
    private val collectionName = "global_alerts"

    suspend fun toggleAlertActive(alertId: String, active: Boolean) {
        db.collection(collectionName)
            .document(alertId)
            .update(mapOf("active" to active)) //o nazwie pola w bazie (bez "is")
            .await()
    }

    suspend fun deleteAlert(alertId: String) {
        db.collection(collectionName)
            .document(alertId)
            .delete()
            .await()
    }
}