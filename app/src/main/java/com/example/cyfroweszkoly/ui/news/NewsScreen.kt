package com.example.cyfroweszkoly.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cyfroweszkoly.data.model.RssItem
import com.example.cyfroweszkoly.viewmodel.NewsUiState
import com.example.cyfroweszkoly.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    navController: NavController,
    // Funkcja viewModel() sama tworzy obiekt NewsViewModel lub pobiera
    // istniejący (np. gdy użytkownik obróci ekran)
    viewModel: NewsViewModel = viewModel()
) {
    // Podłączenie widoku pod nasz strumień stanu z ViewModelu
    val uiState by viewModel.uiState.collectAsState()

    // Box z wyśrodkowaniem na wypadek ładowania lub błędu
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // "switch" (when) sprawdzający aktualny stan
        when (val state = uiState) {
            is NewsUiState.Loading -> {
                // Ekran ładowania - kręcące się kółeczko
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }

            is NewsUiState.Error -> {
                // Ekran błędu z przyciskiem do ponownej próby
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ups! Coś poszło nie tak:", color = MaterialTheme.colorScheme.error)
                    Text(text = state.message, modifier = Modifier.padding(16.dp))
                    Button(onClick = { viewModel.fetchNews() }) {
                        Text("Spróbuj ponownie")
                    }
                }
            }

            is NewsUiState.Success -> {
                // Sukces! Renderujemy listę z danymi
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp), // Marginesy wokół całej listy
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Odstępy między kafelkami
                ) {
                    items(state.news) { newsItem ->
                        NewsCard(newsItem = newsItem) {
                            // Tu na razie wydrukujemy link w konsoli (Logcat).
                            // Później zrobimy tu kod otwierający pełny artykuł
                            println("Kliknięto w artykuł: ${newsItem.link}")
                        }
                    }
                }
            }
        }
    }
}

// Komponent odpowiadający za wygląd pojedynczego kafelka wiadomości
@Composable
fun NewsCard(newsItem: RssItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Tytuł
            Text(
                text = newsItem.title ?: "Brak tytułu",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Data
            Text(
                text = newsItem.pubDate ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Treść (Opis)
            Text(
                text = newsItem.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3, // Obcinamy tekst do maksymalnie 3 linijek (zajawka)
                overflow = TextOverflow.Ellipsis // Dodaje wielokropek "..." na końcu, jeśli tekst jest za długi
            )
        }
    }
}