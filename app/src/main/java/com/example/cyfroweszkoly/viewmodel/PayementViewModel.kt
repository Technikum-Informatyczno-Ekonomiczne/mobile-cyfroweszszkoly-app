package com.example.cyfroweszkoly.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.repository.PaymentRepository
import kotlinx.coroutines.launch

sealed class PaymentInfoState {
    object Loading : PaymentInfoState()
    // Zmieniamy to:
    data class Success(val contactInfo: String, val pricingInfo: String) : PaymentInfoState()
    data class Error(val message: String) : PaymentInfoState()
}

class PaymentViewModel : ViewModel() {

    // Inicjalizacja repozytorium ( Dagger/Hilt później)
    private val repository = PaymentRepository()

    private val _uiState = mutableStateOf<PaymentInfoState>(PaymentInfoState.Loading)
    val uiState: State<PaymentInfoState> = _uiState

    init {
        loadPaymentDetails()
    }

    private fun loadPaymentDetails() {
        viewModelScope.launch {
            _uiState.value = PaymentInfoState.Loading

            // ViewModel po prostu odbiera gotowy rezultat z Repozytorium
            val result = repository.fetchCleanPaymentInfo()

            result.fold(
                onSuccess = { data ->
                    _uiState.value = PaymentInfoState.Success(data.first, data.second)
                },
                onFailure = { exception ->
                    _uiState.value = PaymentInfoState.Error(exception.message ?: "Nieznany błąd")
                }
            )
        }
    }
}