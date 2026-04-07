package com.example.cyfroweszkoly.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.example.cyfroweszkoly.navigation.Screen
import kotlinx.coroutines.selects.select

@Composable
fun DrawerContent(onNavigate: (String)->Unit){
    ModalDrawerSheet {
        Text(
            text = "Menu Główne",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Strona główna")},
            selected = false,
            onClick = {onNavigate(Screen.Home.route)}
        )

        NavigationDrawerItem(
            label = { Text("O aplikacji")},
            selected = false,
            onClick = { onNavigate(Screen.AboutApplication.route)}
        )

        NavigationDrawerItem(
            label = { Text("O autorach")},
            selected = false,
            onClick = { onNavigate(Screen.AboutUs.route)}
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview(){
    MaterialTheme() {
        DrawerContent(onNavigate = {route -> })

    }
}