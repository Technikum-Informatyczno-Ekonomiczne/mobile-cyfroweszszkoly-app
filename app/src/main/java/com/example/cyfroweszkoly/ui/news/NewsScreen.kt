package com.example.cyfroweszkoly.ui.news


import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cyfroweszkoly.data.model.WpPost // Upewnij się, że ten import prowadzi do Twojej klasy WpPost!
import com.example.cyfroweszkoly.viewmodel.NewsUiState
import com.example.cyfroweszkoly.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is NewsUiState.Loading -> {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }

            is NewsUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ups! Coś poszło nie tak:", color = MaterialTheme.colorScheme.error)
                    Text(text = state.message, modifier = Modifier.padding(16.dp))
                    Button(onClick = { viewModel.fetchNews() }) {
                        Text("Spróbuj ponownie")
                    }
                }
            }

            is NewsUiState.Success -> {
                // Pobieramy Context potrzebny do otwarcia przeglądarki
                val context = LocalContext.current

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.news) { newsItem ->
                        NewsCard(newsItem = newsItem) {
                            // Wywołujemy naszą funkcję otwierającą Custom Tab
                            openCustomTab(context, newsItem.link)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: WpPost, onClick: () -> Unit) { // Zmieniono na WpPost
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ZDJĘCIE
            AsyncImage(
                model = newsItem.imageUrl, // Teraz prawidłowo odczyta URL z JSON-a
                contentDescription = "Miniatura artykułu",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // TEKSTY (Teraz poprawnie zamknięte w pionowej kolumnie, która zajmuje resztę miejsca)
            Column(modifier = Modifier.weight(1f)) {
                // Tytuł
                Text(
                    text = newsItem.cleanTitle, // Używamy wyczyszczonego tytułu
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Data (Ucinamy do 10 znaków, żeby usunąć godzinę i zostawić format RRRR-MM-DD)
                Text(
                    text = newsItem.date.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Treść (Opis)
                Text(
                    text = newsItem.cleanExcerpt, // Używamy wyczyszczonego opisu
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Narzędzie do otwierania przeglądarki Chrome Custom Tabs
fun openCustomTab(context: Context, url: String) {
    if (url.isEmpty()) return
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}







