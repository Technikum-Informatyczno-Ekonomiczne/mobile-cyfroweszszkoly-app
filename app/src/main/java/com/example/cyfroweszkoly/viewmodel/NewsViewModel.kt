package com.example.cyfroweszkoly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyfroweszkoly.data.api.NetworkModule
import com.example.cyfroweszkoly.data.model.RssItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//w jakich "stanach" może być nasz ekran aktualności.
//  - ładuje się,
//  - udało się pobrać, albo
//  - wystąpił błąd (np. brak internetu).
sealed class NewsUiState {
    object Loading : NewsUiState() // Kręcące się kółeczko ładowania
    data class Success(val news: List<RssItem>) : NewsUiState() // Mamy listę artykułów!
    data class Error(val message: String) : NewsUiState() // Coś poszło nie tak
}

class NewsViewModel : ViewModel() {

    // Prywatny strumień, do którego tylko ViewModel może wrzucać dane
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)

    // Publiczny strumień, który Compose będzie tylko "obserwował"
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        // Gdy tylko ViewModel powstanie, od razu zaczynamy pobierać newsy
        fetchNews()
    }

    fun fetchNews() {
        // viewModelScope.launch oznacza:
        // "Uruchom to w tle, żeby nie zablokować interfejsu użytkownika"
        viewModelScope.launch {
            // Najpierw ustawiamy stan na ładowanie
            _uiState.value = NewsUiState.Loading

            try {
                // Wywołujemy naszego gotowego
                // Retrofita (którego stworzyliśmy wcześniej)
                val response = NetworkModule.api.getNewsFeed()

                // Wyciągamy listę wiadomości.
                // Jeśli z jakiegoś powodu będzie pusta (null),
                // dajemy pustą listę emptyList()
                val items = response.channel?.items ?: emptyList()

                // Udało się! Przekazujemy listę do stanu Success
                _uiState.value = NewsUiState.Success(items)

            } catch (e: Exception) {
                // Złapaliśmy błąd (np. brak internetu albo serwer szkoły nie odpowiada)
                _uiState.value = NewsUiState.Error("Nie udało się pobrać aktualności: ${e.localizedMessage}")
            }
        }
    }
}