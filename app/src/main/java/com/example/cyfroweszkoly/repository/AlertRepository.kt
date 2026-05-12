package com.example.cyfroweszkoly.repository

import com.example.cyfroweszkoly.data.model.AlertModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
}