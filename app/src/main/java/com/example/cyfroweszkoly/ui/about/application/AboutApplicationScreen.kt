package com.example.cyfroweszkoly.ui.about.application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutApplicationScreen(navController: NavController) {
    // Dodajemy scrollState, ponieważ tekst jest długi i może nie zmieścić się na ekranie
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Dodajemy marginesy od krawędzi ekranu
            .verticalScroll(scrollState), // Włączamy przewijanie
        horizontalAlignment = Alignment.Start // Wyrównanie do lewej (bardziej czytelne dla tekstu)
    ) {
        // Główny Nagłówek
        Text(
            text = "Dokumentacja Systemu / O Aplikacji",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Wstęp
        Text(
            text = "Nasz system jest efektem ścisłej współpracy mentora (nauczyciela)" +
                    " oraz zespołu inżynierów-stażystów z Technikum nr 9." +
                    " Projekt realizowany jest w formule non-profit przez" +
                    " grupę pasjonatów, koncentrując się na implementacji nowoczesnych wzorców " +
                    "architektury oprogramowania oraz technologii cloud-native.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Sekcja 1: Specyfikacja Techniczna
        TextHeader(text = "Specyfikacja Techniczna i Architektoniczna")

        BulletPoint(title = "Platforma & Język", description = "Aplikacja rozwijana jest w ekosystemie Androida, wykorzystując język Kotlin (standard Kotlin-first).")
        BulletPoint(title = "Interfejs Użytkownika", description = "Warstwa prezentacji została zaimplementowana z użyciem deklaratywnego frameworka UI — Jetpack Compose, co zapewnia modularność i reaktywność interfejsu.")

        // Przykład użycia AnnotatedString do pogrubienia słów kluczowych wewnątrz opisu
        val firebaseDescription = buildAnnotatedString {
            append("Architektura opiera się na usługach Serverless platformy Firebase. Wykorzystujemy ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Firestore") }
            append(" jako nierelacyjną bazę danych (NoSQL) do synchronizacji danych w czasie rzeczywistym oraz ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Firebase Authentication") }
            append(" do zarządzania tożsamością użytkowników.")
        }
        BulletPoint(title = "Warstwa Danych & Backend", annotatedDescription = firebaseDescription)

        Spacer(modifier = Modifier.height(24.dp))

        // Sekcja 2: Metodyka
        TextHeader(text = "Metodyka Pracy i Inżynieria Procesu")

        BulletPoint(title = "Zarządzanie Cyklem Życia Oprogramowania", description = "Cały kod źródłowy jest hostowany i wersjonowany w repozytorium GitHub. Proces rozwoju opiera się na Pull Requests oraz Code Review, co gwarantuje wysoką jakość kodu i transfer wiedzy w zespole.")
        BulletPoint(title = "Komunikacja & Synchronizacja", description = "Do operacyjnego zarządzania projektem, dokumentacji decyzji technicznych oraz komunikacji synchronicznej wykorzystujemy platformę Discord.")

        Spacer(modifier = Modifier.height(24.dp))

        // Podsumowanie
        Text(
            text = "To podejście pozwala nam nie tylko na dostarczenie działającego produktu, ale przede wszystkim na symulację profesjonalnego, inżynieryjnego środowiska pracy.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

// Pomocniczy Composable dla nagłówków sekcji
@Composable
fun TextHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

// Pomocniczy Composable dla punktów listy (z pogrubionym tytułem)
@Composable
fun BulletPoint(title: String, description: String? = null, annotatedDescription: androidx.compose.ui.text.AnnotatedString? = null) {
    Column(modifier = Modifier.padding(bottom = 12.dp, start = 8.dp)) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)) {
                    append("• $title: ")
                }
            },
            style = MaterialTheme.typography.bodyLarge
        )
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        } else if (annotatedDescription != null) {
            Text(
                text = annotatedDescription,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}