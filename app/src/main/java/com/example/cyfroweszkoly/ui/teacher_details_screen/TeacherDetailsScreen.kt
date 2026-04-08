package com.example.cyfroweszkoly.ui.teacher_details_screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.model.ScheduleEntry
import com.example.cyfroweszkoly.viewmodel.TeacherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDetailsScreen(
    teacherId: Int,
    viewModel: TeacherViewModel,
    onBackClick: ()->Unit
){

    val teacherName = viewModel.getTeacherName(teacherId)
    val daysOfWeek = listOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek")


    // 1. Pobieramy aktualny dzień tygodnia (1 = Poniedziałek, 7 = Niedziela)
    val currentDay = java.time.LocalDate.now().dayOfWeek.value
    // 2. Mapujemy na indeks Twojej listy (Pon-Pt to 0-4)
    // Jeśli jest sobota (6) lub niedziela (7), ustawiamy domyślnie Poniedziałek (0)
    val initialIndex = if (currentDay <= 5) currentDay - 1 else 0
    // 3. Inicjalizujemy stan tym obliczonym indeksem
    var selectedDayIndex by remember { mutableStateOf(initialIndex) }

    // Pobieramy plan lekcji dla tego nauczyciela na konkretny wybrany dzień
    val dailySchedule = viewModel.getScheduleForTeacherAndDay(teacherId, daysOfWeek[selectedDayIndex])

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Plan zajęć") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Wróć")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = teacherName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Gdzie go teraz znajdziesz?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SecondaryScrollableTabRow(
                selectedTabIndex = selectedDayIndex,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                daysOfWeek.forEachIndexed { index, day ->
                    Tab(
                        selected = selectedDayIndex == index,
                        onClick = { selectedDayIndex = index },
                        text = { Text(day) })

                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            // LISTA PLANU LEKCJI LUB INFORMACJA O BRAKU ZAJĘĆ
            if (dailySchedule.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Brak zajęć w tym dniu 🎉",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(dailySchedule) { entry ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                    alpha = 0.5f
                                )
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // KOLUMNA 1: Numer lekcji w stylowym kółku
                                Surface(
                                    modifier = Modifier.size(40.dp),
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = entry.lessonNumber.toString(),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // KOLUMNA 2: Dane o lekcji
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = entry.time,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = entry.location,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = entry.className,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                // KOLUMNA 3: Ikona (opcjonalnie)
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TeacherDetailsScreenPreview(){
    TeacherDetailsScreen(
        viewModel = TeacherViewModel(),
        teacherId = 1,
        onBackClick = {})

}