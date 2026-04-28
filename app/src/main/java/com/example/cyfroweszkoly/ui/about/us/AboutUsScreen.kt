package com.example.cyfroweszkoly.ui.about.us

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyfroweszkoly.data.model.Author

// Wewnątrz Twojego Composable:
val authors = listOf(
    Author(
        name = "Arcymag Jarosław Strzelecki",
        classAndLore = "Główny Mistrz Gildii (Nauczyciel) i " +
                "Strażnik Tajemnego Kodu. " +
                "To on doznał proroczej wizji stworzenia tego cyfrowego " +
                "królestwa. Jako Inżynier Oprogramowania po nocach wykuwa " +
                "najtwardsze runy (koder) i koordynuje całą kampanię."
    ),
    Author(
        name = "Szymon Haponik (Zakon IVB TIE)",
        classAndLore = "Dwuklasowiec: łączy potężne zaklęcia Iluzji " +
                "(grafika) z rzemiosłem Runotwórstwa (kodowanie)." +
                " Kiedy nie włada mieczem w walce z błędami, maluje" +
                " magiczne witraże dla naszego interfejsu."
    ),
    Author(
        name = "Marysia Chałupa (Loża IIIB TIE)",
        classAndLore = "Nadworna Kartografka i Widząca. " +
                "Posługując się starożytnymi artefaktami wyobraźni, " +
                "wyczarowała pierwsze mapy naszego świata. " +
                "Nakreśliła plany Głównej Bramy i fortec poszczególnych" +
                " frakcji (szkół)."
    )
)
@Composable
fun AboutUsScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nagłówek
        Text(
            text = "o Autorach",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista (LazyColumn zajmuje dostępną przestrzeń dzięki weight(1f))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                    items(authors) { author ->
                        AuthorItem( author)
                    }
                }

                // Stopka
                Text(
                    text = "2025/2026",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
}

@Composable
fun AuthorItem(member: Author) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp), // Przyjemne zaokrąglenia
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikonka/Awatar postaci
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star, // Zastępcza ikona (gwiazdka)
                    contentDescription = "Ranga bohatera",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Teksty (Imię + Klasa)
            Column {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary // Wyróżniamy imię głównym kolorem aplikacji
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = member.classAndLore,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic // Kursywa dla klimatu opowieści
                )
            }
        }
    }
}