package com.example.cyfroweszkoly.repository

import com.example.cyfroweszkoly.data.model.AlertModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlin.getValue

class AlertRepository {
    private val db by lazy { Firebase.firestore }
    private val collectionName = "global_alerts"

    // Zwracamy 'Flow' - to ciągły strumień danych,
    // który sam reaguje na zmiany w chmurze
    fun getActiveAlerts(): Flow<List<AlertModel>> {
        return db.collection(collectionName)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents
                    .mapNotNull { document ->
                        // Natywne mapowanie dokumentu Firestore na klasę Kotlina
                        document.toObject(AlertModel::class.java)
                    }
                    .filter { it.isActive }
            }
    }

    suspend fun addAlert(title: String, message: String, isUrgent: Boolean){
        val newDocRef = db
            .collection(collectionName)
            .document()

        val alert = AlertModel(
            id = newDocRef.id,
            title=title,
            message=message,
            isUrgent=isUrgent,
            isActive = true
        )

        // wysyłanie do chmury
        newDocRef.set(alert).await()

    }

    suspend fun toggleAlertActive(alertId: String, active: Boolean) {
        db.collection(collectionName)
            .document(alertId)
            .update(mapOf("active" to active)) //o nazwie pola w bazie (bez "is")
            .await()
    }

    fun getAllAlertsForAdmin(): Flow<List<AlertModel>> {
        return db.collection(collectionName)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents
                    .mapNotNull { it.toObject(AlertModel::class.java) }
                // Sortujemy od najnowszych
            }
    }

    suspend fun deleteAlert(alertId: String) {
        db.collection(collectionName)
            .document(alertId)
            .delete()
            .await()
    }
}