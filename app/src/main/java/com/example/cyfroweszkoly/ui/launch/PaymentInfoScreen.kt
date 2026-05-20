package com.example.cyfroweszkoly.ui.launch

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.ui.launch.PaymentInfoCard
import com.example.cyfroweszkoly.viewmodel.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentInfoScreen(
    viewModel: PaymentViewModel,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Informacje o opłatach") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Wróć"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Przekazujemy zainicjalizowany ViewModel do karty
            PaymentInfoCard(viewModel = viewModel)
        }
    }
}