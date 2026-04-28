package com.example.cyfroweszkoly.ui.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.model.Achievement
import com.example.cyfroweszkoly.model.AchievementDetail
import com.google.firebase.Timestamp

@Composable
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Tytuł
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagi (Wyświetlane w poziomie)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(achievement.tags) { tag ->
                    AssistChip(
                        onClick = { /* TODO: Akcja filtrowania po kliknięciu w tag */ },
                        label = { Text(tag, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Główny opis
            Text(
                text = achievement.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Renderowanie elastycznych sekcji (np. wyników z egzaminu lub zawodów)
            achievement.details.forEach { detail ->
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = detail.subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = detail.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // TODO: Tutaj można dodać AsyncImage
        //  (np. z biblioteki Coil) do wyświetlania zdjęć z imageUrls
        }
    }
}



// 1. Prosty obiekt testowy (Mock Data)
val sampleAchievement = Achievement(
    id = "test_id_001",
    title = "I miejsce w Wojewódzkim Konkursie Matematycznym",
    date = Timestamp.now(),
    schoolType = "LO",
    tags = listOf("Matematyka", "Konkurs", "LO", "Sukces"),
    description = "Nasz uczeń, Jan Kowalski, zajął pierwsze miejsce w prestiżowym konkursie matematycznym, rozwiązując wszystkie zadania przed czasem.",
    imageUrls = emptyList(), // Brak zdjęć w tym prostym przykładzie
    details = listOf(
        AchievementDetail(
            subtitle = "Kategoria",
            content = "Licea Ogólnokształcące - Klasy 3"
        ),
        AchievementDetail(
            subtitle = "Wynik",
            content = "98/100 punktów (Najlepszy wynik w województwie)"
        )
    )
)

// 2. Funkcja podglądu dla Android Studio
@Preview(showBackground = true, widthDp = 360)
@Composable
fun AchievementCardPreview() {
    // Opakowanie w MaterialTheme zapewnia poprawne kolory i fonty w podglądzie
    MaterialTheme {
        AchievementCard(achievement = sampleAchievement)
    }
}




