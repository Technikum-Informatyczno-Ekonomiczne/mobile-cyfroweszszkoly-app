package com.example.cyfroweszkoly.ui.schools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ScoreItem(subject: String, percentage: Float, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Górny wiersz: Nazwa przedmiotu i % po prawej
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subject,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(
                text = "${percentage}%",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                color = color // Wynik w kolorze paska
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Pasek postępu
        LinearProgressIndicator(
            progress = { percentage / 100f }, // Compose oczekuje wartości od 0.0 do 1.0
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp) // Grubość paska
                .clip(RoundedCornerShape(5.dp)), // Zaokrąglone końcówki
            color = color,
            trackColor = Color.LightGray.copy(alpha = 0.5f) // Tło paska
        )
    }
}