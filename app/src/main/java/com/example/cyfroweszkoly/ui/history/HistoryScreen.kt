package com.example.cyfroweszkoly.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.navigation.AppNavHost
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme



@Composable
fun HistoryScreen(navController: NavController){
    Column(

    ) {
        Text(text="Ekran historii szkoły")

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview(){
    val navController = rememberNavController()
    CyfroweSzkolyTheme {
        HistoryScreen(navController)
    }
}



