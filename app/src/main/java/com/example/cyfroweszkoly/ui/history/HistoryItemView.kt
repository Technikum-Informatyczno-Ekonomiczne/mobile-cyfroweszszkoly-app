package com.example.cyfroweszkoly.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.models.HistoryItem
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme


/**
 * Pojedynczy element historii szkoły
 *
 * @param item obiekt historii do wyświetlenia
 */
@Composable
fun HistoryItemView(item: HistoryItem){
    Column{
        Text(text = item.date)
        Text(text = item.title)
        Text(text = item.description)
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItemViewPreview() {
    CyfroweSzkolyTheme {
        HistoryItemView(
            item = HistoryItem(
                date = "2023-10-26",
                title = "Sample Title",
                image = null,
                description = "This is a sample description for the history item."
            )
        )
    }
}
