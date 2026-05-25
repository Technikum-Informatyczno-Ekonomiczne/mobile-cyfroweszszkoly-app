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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//import com.example.cyfroweszkoly.data.model.Teacher
//import com.example.cyfroweszkoly.viewmodel.TeacherViewModel
// zakomentowano stary import modelu Teacher
// Importujemy enum z naszego nowego ViewModelu
import com.example.cyfroweszkoly.viewmodel.SearchType
import com.example.cyfroweszkoly.viewmodel.SearchAutocompleteViewModel

@Composable
fun FindTeacherScreen(
    viewModel: SearchAutocompleteViewModel,
    onTeacherClick: (String) -> Unit,
    onBackClick: () -> Unit
) {

    //  Obserwujemy strumień podpowiedzi (to ten asStateFlow z ViewModelu!)
    val suggestions by viewModel.suggestions.collectAsState()
    // Lokalny stan dla wpisywanego tekstu (zastępuje stare viewModel.searchQuery)
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onSearchQueryChanged("", SearchType.TEACHER)
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
            onValueChange = {   newValue ->
                searchQuery = newValue
                // Przekazujemy wpisaną literkę do ViewModelu, by przefiltrował lokalną listę
                viewModel.onSearchQueryChanged(newValue, SearchType.TEACHER)
            },
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


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(suggestions) { teacherName ->
                // Wygląd pojedynczego wiersza z nauczycielem
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTeacherClick(teacherName) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = teacherName, style = MaterialTheme.typography.titleMedium)

                    }
                }
            }
        }
    }
}
