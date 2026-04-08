package com.example.cyfroweszkoly.ui.schools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyfroweszkoly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechSchoolScreen(navController: NavController?){
        // Główny kontener całego ekranu - domyślnie ustawiamy mu całkowicie białe tło
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            // --- 1. FIOLETOWY BANER (NAGŁÓWEK) ---
           SchoolBanner(
               title = "TIE 9",
               iconResId = R.drawable.ic_technikum,
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
                    text = "TIE9 to nowoczesna szkoła średnia," +
                            " która łączy edukację techniczną z pasją do nowych technologii."
                           ,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray // Delikatnie szary, aby czytanie nie męczyło oczu
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- LISTA KIERUNKÓW ---
                // Używamy naszej nowej, dedykowanej funkcji dla każdego elementu
                ProfileItem(profession = "Technik informatyk", details = "profil e-sportowy")
                ProfileItem(profession = "Technik programista", details = "robotyka i obsługa dronów")
                ProfileItem(profession = "Technik reklamy", details = "grafika komputerowa")
                ProfileItem(profession = "Technik BHP", details = "kurs MS Office")
                ProfileItem(profession = "Technik logistyk", details = "kurs MS Office")
                ProfileItem(profession = "Technik fotografii i multimediów", details = "kurs MS Office")
                ProfileItem(profession = "Technik cyberbezpieczeństwa", details = "kursy Cisco")
            }
        }
    }


//
//@Preview(showBackground = true)
//@Composable
//fun TechSchoolScreenPreview(){
//    TechSchoolScreen(null)
//}