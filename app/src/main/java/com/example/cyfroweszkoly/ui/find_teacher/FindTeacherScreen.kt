package com.example.cyfroweszkoly.ui.find_teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun FindTeacherScreen(navController: NavController){
    Column() {
        Text("Znajdź nauczyciela")

    }
}


@Preview(showBackground = true)
@Composable
fun FindTeacherScreenPreview(){
    MaterialTheme {
        FindTeacherScreen(navController = rememberNavController())
    }
}