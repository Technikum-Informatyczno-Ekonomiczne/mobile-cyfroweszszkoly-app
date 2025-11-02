package com.example.cyfroweszkoly.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.navigation.Screen
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme


@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(route = Screen.Primary.route)}) {
            Text(text = "Szkoła podstawowa")
        }
        Button(onClick = {navController.navigate(route = Screen.High.route )}){
            Text(text = "Liceum IX")
        }
        Button(onClick = { navController.navigate(route = Screen.Tech.route )}) {
            Text(text = "Technikum XI")
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController()
    CyfroweSzkolyTheme {
        HomeScreen(navController)
    }
}


/*

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(Screen.Primary.route) }) { Text("Szkoła Podstawowa") }
        Button(onClick = { navController.navigate(Screen.High.route) }) { Text("Liceum") }
        Button(onClick = { navController.navigate(Screen.Tech.route) }) { Text("Technikum") }
    }
}
 */
