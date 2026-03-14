package com.example.cyfroweszkoly.ui.history

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
        Button(onClick = { navController.navigate(route = Screen.Primary.route)}) {
            Text(text = "Szkoła podstawowa")
        }
        Image(
            painter = painterResource(R.drawable.liceum_button),
            contentDescription = "Liceum IX",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
                .clip(RoundedCornerShape(40.dp))
                .clickable { navController.navigate(Screen.High.route) }
        )
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
