package com.example.cyfroweszkoly.ui.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.R
import com.example.cyfroweszkoly.navigation.Screen
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme


@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.sp_button),
            contentDescription = "Szkoła podstawowa 311",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
                .clip(RoundedCornerShape(40.dp))
                .clickable { navController.navigate(Screen.Primary.route) }
        )
        Image(
            painter = painterResource(R.drawable.liceum_button),
            contentDescription = "Liceum XI",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(0.dp ,20.dp ,0.dp ,20.dp)
                .fillMaxWidth(0.7f)
                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
                .clip(RoundedCornerShape(40.dp))
                .clickable { navController.navigate(Screen.High.route) }
        )
        Image(
            painter = painterResource(R.drawable.technikum_button),
            contentDescription = "Technikum IX",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
                .clip(RoundedCornerShape(40.dp))
                .clickable { navController.navigate(Screen.Tech.route) }
        )


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
