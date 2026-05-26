package com.example.cyfroweszkoly.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.model.WpPost
import com.example.cyfroweszkoly.data.api.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sukces zwraca teraz List<WpPost> zamiast List<RssItem>
sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val news: List<WpPost>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(value=NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading

            try {
                //ZMIANA TUTAJ: Nasze API zwraca od razu listę postów (JSON)
                val response = NetworkModule.api.getNewsFeed()

                // Przekazujemy listę postów do stanu sukcesu
                _uiState.value = NewsUiState.Success(response)

            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error("Nie udało się pobrać aktualności: ${e.localizedMessage}")
            }
        }
    }
}