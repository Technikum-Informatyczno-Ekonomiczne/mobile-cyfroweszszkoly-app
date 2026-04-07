package com.example.cyfroweszkoly.ui.teacher_details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.models.ScheduleEntry

fun getTeacherSchedule(teacherId: Int): List<ScheduleEntry> {
    return listOf(
        ScheduleEntry("08:00 - 08:45", "Sala 102 (Liceum)", "Klasa 1A"),
        ScheduleEntry("08:55 - 09:40", "Sala 12 (Podstawówka)", "Klasa 4C"),
        ScheduleEntry("09:40 - 09:50", "Korytarz Parter (Podstawówka)", "DYŻUR"),
        ScheduleEntry("09:50 - 10:35", "Sala 204 (Technikum)", "Klasa 3T"),
        ScheduleEntry("10:45 - 11:30", "Pokój Nauczycielski", "Okienko")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDetailsScreen(
    teacherId: Int,
    onBackClick: ()->Unit
){
    val teacherName = if (teacherId == 1) "Jan Kowalski" else "Nauczyciel #$teacherId"

    // Pobieramy plan zajęć dla tego nauczyciela
    val schedule = getTeacherSchedule(teacherId)

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
                    fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Gdzie go teraz znajdziesz?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista z planem zajęć
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(schedule) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = "Lokalizacja",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = entry.time,
                                    style = MaterialTheme.typography.labelMedium)
                                Text(text = entry.location,
                                    style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                                Text(text = entry.className,
                                    style = MaterialTheme.typography.bodyMedium)
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
    TeacherDetailsScreen(teacherId = 1, onBackClick = {})

}