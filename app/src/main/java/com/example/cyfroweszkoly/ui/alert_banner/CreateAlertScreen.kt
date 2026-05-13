package com.example.cyfroweszkoly.ui.alert_banner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.viewmodel.AdminAlertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlertScreen(
    viewModel: AdminAlertViewModel,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wyślij nowy komunikat") },
                // Tu możesz dodać ikonkę strzałki powrotu (onBackClick)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Tytuł (np. Zablokowany wyjazd)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.message,
                onValueChange = { viewModel.message = it },
                label = { Text("Treść komunikatu") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                maxLines = 5
            )

            // Przełącznik "Czy to pilne?"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Switch(
                    checked = viewModel.isUrgent,
                    onCheckedChange = { viewModel.isUrgent = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (viewModel.isUrgent) "PILNE (Czerwony pasek)" else "Zwykła informacja (Niebieski pasek)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Przycisk wysyłania
            Button(
                onClick = {
                    viewModel.sendAlert(onSuccess = {
                        onBackClick() // Wracamy do poprzedniego ekranu po wysłaniu
                    })
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !viewModel.isSending && viewModel.title.isNotBlank() && viewModel.message.isNotBlank()
            ) {
                if (viewModel.isSending) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Wyślij do całej szkoły")
                }
            }
        }
    }
}