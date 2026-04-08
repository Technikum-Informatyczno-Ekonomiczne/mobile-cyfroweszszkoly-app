package com.example.cyfroweszkoly.ui.schools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun HighSchoolScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SchoolBanner(
            title = "LO XII",
            iconResId = R.drawable.ic_technikum,
            backgroundColor = Color(0xFF2196F3)
        )

        // --- 2. BIAŁA STREFA NA INFORMACJE ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Marginesy dla samego tekstu
        ) {
            Text(
                text = "O naszym technikum",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black // Czarne litery na białym tle
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TXI LO to dynamicznie rozwijające się" +
                        " liceum ogólnokształcące, które oferuje szeroki wybór profili " +
                        "kształcenia dostosowanych do zainteresowań i planów edukacyjnych" +
                        " uczniów. Szkoła stawia na nowoczesne metody nauczania, " +
                        "rozwój kompetencji językowych oraz przygotowanie do " +
                        "studiów wyższych w kraju i za granicą.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray // Delikatnie szary, aby czytanie nie męczyło oczu
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- LISTA KIERUNKÓW ---
            ProfileItem(
                profession = "Profil medyczny",
                details = "rozszerzenia: biologia, chemia"
            )
            ProfileItem(
                profession = "Profil medyczny z matematyką",
                details = "rozszerzenia: biologia, chemia, matematyka"
            )
            ProfileItem(
                profession = "Profil politechniczny",
                details = "rozszerzenia: matematyka, fizyka, j. angielski"
            )
            ProfileItem(
                profession = "Profil prawniczo-dziennikarski",
                details = "rozszerzenia: j. polski, historia, WOS"
            )
            ProfileItem(
                profession = "Profil przyrodniczo-humanistyczny",
                details = "rozszerzenia: biologia, j. polski"
            )
            ProfileItem(
                profession = "Profil uniwersytecki",
                details = "rozszerzenia: j. angielski, geografia, matematyka"
            )
            ProfileItem(
                profession = "Profil dwujęzyczny",
                details = "po ang.: biol., hist., geogr., fiz. | rozsz: biologia, j. angielski"
            )
        }
    }
}