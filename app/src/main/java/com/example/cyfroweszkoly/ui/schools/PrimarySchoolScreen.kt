package com.example.cyfroweszkoly.ui.schools

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyfroweszkoly.R


@Composable
fun PrimarySchoolScreen(navController: NavController){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        SchoolBanner(
            title = "SP 311",
            iconResId = R.drawable.ic_primary,
            backgroundColor = Color(0xFF048B77)
        )

        // --- 2. BIAŁA STREFA NA INFORMACJE ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Marginesy dla samego tekstu
        ) {
            Text(
                text = "O naszej Szkole Podstawowej nr 311",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black // Czarne litery na białym tle
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SP311 to nowoczesna szkoła podstawowa, " +
                        "która łączy wysoki poziom nauczania z rozwijaniem pasji i " +
                        "talentów uczniów. Oferuje klasy dwujęzyczne, sportowe oraz ogólne," +
                        " zapewniając wszechstronny rozwój dzieci i młodzieży.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray // Delikatnie szary, aby czytanie nie męczyło oczu
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // --- OFERTA EDUKACYJNA ---
                Text(
                    text = "Oferta edukacyjna",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Używamy tego samego ProfileItem, co w technikum i LO!
                ProfileItem(
                    profession = "Oddziały dwujęzyczne",
                    details = "Intensywna nauka języka angielskiego od najmłodszych lat."
                )
                ProfileItem(
                    profession = "Zajęcia dodatkowe",
                    details = "Koła zainteresowań, projekty edukacyjne, wycieczki."
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- OSIĄGNIĘCIA (WYNIKI EGZAMINÓW) ---
                Text(
                    text = "Wyniki egzaminów ósmoklasisty",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Używamy naszego nowego komponentu do pasków postępu
                ScoreItem(subject = "Język angielski", percentage = 92.53f, color = Color(0xFF4CAF50))
                ScoreItem(subject = "Matematyka", percentage = 73.06f, color = Color(0xFF2196F3))
                ScoreItem(subject = "Język polski", percentage = 72.31f, color = Color(0xFFE91E63))

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        }
    }
