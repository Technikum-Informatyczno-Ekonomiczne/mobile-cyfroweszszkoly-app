package com.example.cyfroweszkoly.ui.schools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SchoolBanner(
    title: String,
    iconResId: Int, // Przekazujemy ID naszej ikonki
    backgroundColor: Color = Color(0xFF986CD7) // Twój fiolet ustawiony jako domyślny!
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        color = backgroundColor,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black
                ),
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "Ikona szkoły $title",
                tint = Color.Unspecified,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}