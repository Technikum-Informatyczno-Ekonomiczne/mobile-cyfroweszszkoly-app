package com.example.cyfroweszkoly.ui.search

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
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab

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
import com.example.cyfroweszkoly.ui.search.SearchAutocompleteViewModel

@Composable
fun GlobalSearchScreen(
    viewModel: SearchAutocompleteViewModel,
    onResultClick: (String, SearchType) -> Unit,
    onBackClick: () -> Unit
) {

    //  Obserwujemy strumień podpowiedzi (to ten asStateFlow z ViewModelu!)
    val suggestions by viewModel.suggestions.collectAsState()
    // Lokalny stan dla wpisywanego tekstu (zastępuje stare viewModel.searchQuery)
    var searchQuery by remember { mutableStateOf("") }

    // Dodajemy lokalny stan dla wybranego trybu (domyślnie Nauczyciel)
    var currentSearchType by remember { mutableStateOf(SearchType.TEACHER) }

    LaunchedEffect(currentSearchType) {
        viewModel.onSearchQueryChanged(query="", type=currentSearchType)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Czego szukasz",
            style = MaterialTheme.typography.headlineMedium
        )

        // pasek zakładek (Tab) do wyboru, czego szukamy
        // Jawnie tłumaczymy stan na pozycję zakładki na ekranie
        val selectedTabIndex = when (currentSearchType) {
            SearchType.TEACHER -> 0
            SearchType.CLASS -> 1
            SearchType.ROOM -> 2
        }
        PrimaryTabRow(selectedTabIndex = selectedTabIndex)
        {
            Tab(
                selected = currentSearchType == SearchType.TEACHER,
                onClick = {currentSearchType = SearchType.TEACHER},
                text = {Text("Nauczyciele")}
            )
            Tab(
                selected = currentSearchType == SearchType.CLASS,
                onClick = {currentSearchType = SearchType.CLASS},
                text = {Text("Klasy")}
            )
            Tab(
                selected = currentSearchType == SearchType.ROOM,
                onClick = {currentSearchType = SearchType.ROOM},
                text = {Text("Sale")}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {   newValue ->
                searchQuery = newValue
                // Przekazujemy wpisaną literkę do ViewModelu, by przefiltrował lokalną listę
                viewModel.onSearchQueryChanged(query=newValue, type=currentSearchType)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                val hint = when(currentSearchType){
                    SearchType.TEACHER -> "Wpisz nazwisko..."
                    SearchType.CLASS -> "Wpisz nazwę klasy (np. 1 TIE)"
                    SearchType.ROOM -> "Wpisz numer sali"
                }
                Text(hint) },
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
            items(suggestions) { result ->
                // Wygląd pojedynczego wiersza z nauczycielem
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // przekazujemy wyżej nazwę, ale też i typ
                            // aby NavHost wiedział, o co potem zapytac bazę
                            onResultClick(result, currentSearchType) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = result, style = MaterialTheme.typography.titleMedium)

                    }
                }
            }
        }
    }
}
