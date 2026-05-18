package com.example.cyfroweszkoly.ui.launch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.data.model.DailyMenu
import com.example.cyfroweszkoly.data.model.MealCourse
import com.example.cyfroweszkoly.viewmodel.LunchViewModel
@Composable
fun LunchScreen(
    viewModel: LunchViewModel,
    onNavigateToPayments: () -> Unit // Przekazujemy akcję nawigacji z zewnątrz
) {
    val menu = viewModel.weeklyMenu

    // Główny kontener - statyczny
    Column(modifier = Modifier.fillMaxSize()) {

        // ZAMROŻONY NAGŁÓWEK (Fixed Header)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // stylizacja tytułu ekranu
            Text(
                text = "Jadłospis",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            // Przycisk "Opłaty" (FilledTonalButton )
            FilledTonalButton(onClick = onNavigateToPayments) {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = "Szczegóły opłat",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Opłaty")
            }
        }

        // 2. PRZEWIJANA LISTA DAŃ
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Odstępy między dniami
        ) {
            items(menu) { dailyMenu ->
                DailyMenuCard(dailyMenu)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                PaymentInfoCard()
            }
        }
    }
}

@Composable
fun DailyMenuCard(dailyMenu: DailyMenu) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Nagłówek: Dzień tygodnia i data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dailyMenu.day,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = dailyMenu.date,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider() // Subtelna linia oddzielająca nagłówek od dań
            Spacer(modifier = Modifier.height(12.dp))

            // Generowanie wierszy dla każdego dania z użyciem pętli
            dailyMenu.courses.forEach { course ->
                MealItemRow(course)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MealItemRow(course: MealCourse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top // Tutaj była poprawka
    ) {
        // Kategoria (np. Zupa, II danie)
        Text(
            text = "${course.category}:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(80.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Główna sekcja z nazwą dania i ewentualnymi alergenami
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.bodyMedium
            )
            if (course.allergens.isNotEmpty()) {
                Text(
                    text = "Alergeny: ${course.allergens}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Gramatura / Porcja wyrównana do prawej
        Text(
            text = course.portion,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}