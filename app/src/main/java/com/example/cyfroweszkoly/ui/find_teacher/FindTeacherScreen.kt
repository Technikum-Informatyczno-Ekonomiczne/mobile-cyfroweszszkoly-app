package com.example.cyfroweszkoly.ui.find_teacher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.cyfroweszkoly.models.Teacher

val dummyTeachers = listOf(
    Teacher(1, "Jan Kowalski", "Matematyka"),
    Teacher(2, "Anna Nowak", "Język Polski"),
    Teacher(3, "Marek Ząb", "Fizyka"),
    Teacher(4, "Katarzyna Wójcik", "Matematyka"),
    Teacher(5, "Piotr Wiśniewski", "Informatyka")
)

@Composable
fun FindTeacherScreen(
    onTeacherClick: (Teacher) -> Unit,
    onBackClick: () -> Unit
) {
    // zmienna do przechowywani informacji o tym
    // co użytkownik wpisał
    var searchQuery by remember { mutableStateOf("") }


    // dynamiczne filtrowanie listy
    val filteredTeachers = dummyTeachers.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.subject.contains(searchQuery, ignoreCase = true)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Kogo szukasz",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Wpisz nazwisko lub przedmiot...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Szukaj"
                )
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Wydajna lista wyników (odpowiednik dawnego RecyclerView)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTeachers) { teacher ->
                // Wygląd pojedynczego wiersza z nauczycielem
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTeacherClick(teacher) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = teacher.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = teacher.subject, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FindTeacherScreenPreview(){
    MaterialTheme {
        FindTeacherScreen(
            onTeacherClick = {},
            onBackClick = {}
        )
    }
}