package com.example.cyfroweszkoly.ui.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.R
import com.example.cyfroweszkoly.data.model.AlertModel
import com.example.cyfroweszkoly.navigation.Screen
import com.example.cyfroweszkoly.ui.components.GlobalAlertBanner
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme
import com.google.firebase.firestore.FirebaseFirestore

// Model pomocniczy do trzymania Twoich danych
data class SchoolItem(
    val imageRes: Int,
    val description: String,
    val route: String,
    val buttonColor: Color
)


@Composable
fun HomeScreen(navController: NavController){
    // 1. Stan dla alertów
    val activeAlerts = remember { mutableStateListOf<AlertModel>() }
    val db = FirebaseFirestore.getInstance()

    // 2. Nasłuchiwanie zmian w czasie rzeczywistym
    DisposableEffect(Unit) {
        val listener = db.collection("global_alerts")
            .whereEqualTo("active", true) // Szukamy tylko aktywnych
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                if (snapshot != null) {
                    activeAlerts.clear()
                    val items = snapshot.toObjects(AlertModel::class.java)
                    activeAlerts.addAll(items)
                }
            }

        // Ważne: usuwamy nasłuchiwanie, gdy wychodzimy z ekranu
        onDispose { listener.remove() }
    }
    // do śledzenia aktualnie klikniętego indeksu z listy szkół
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    val schools = listOf(
        SchoolItem(
            imageRes = R.drawable.sp_button,
            description = "Szkoła podstawowa 311",
            route = Screen.Primary.route,
            buttonColor = Color(0xFF169384)
        ),
        SchoolItem(
            imageRes = R.drawable.liceum_button,
            description = "Liceum XI",
            route = Screen.High.route,
            buttonColor = Color(0xFF267393)
        ),
        SchoolItem(
            imageRes = R.drawable.technikum_button,
            description = "Technikum IX",
            route = Screen.Tech.route,
            buttonColor = Color(0xFF7B6E96)
        )
    )
    Column(modifier = Modifier.fillMaxSize()) {

        // 3. Banner pojawia się na samej górze (tylko jeśli są alerty)
        GlobalAlertBanner(alerts = activeAlerts)

        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            schools.forEachIndexed { index, school ->
                val isExpanded = expandedIndex == index

                // Harmonijka: wybrany element dostaje wagi 2.5, reszta 1.0
                val animatedWeight by animateFloatAsState(
                    targetValue = if (isExpanded) 2.5f else 1f,
                    animationSpec = tween(durationMillis = 400),
                    label = "weightAnimation"
                )

                // Szerokość: wybrany element rozszerza się z 70% do 90% szerokości ekranu
                val animatedWidth by animateFloatAsState(
                    targetValue = if (isExpanded) 0.9f else 0.7f,
                    label = "widthAnimation"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(animatedWidth)
                        .weight(animatedWeight)
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .clickable {
                            if (isExpanded) {
                                // Jeśli uczeń kliknie w obrazek po raz drugi, po prostu wchodzimy!
                                navController.navigate(school.route)
                            } else {
                                // Rozwiń wybraną szkołę
                                expandedIndex = index
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Używamy Fit, aby zachować proporcje obrazka z R.drawable
                    Image(
                        painter = painterResource(school.imageRes),
                        contentDescription = school.description,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(40.dp))
                    )

                    // Sekcja z akcjami - pojawia się TYLKO dla wybranej szkoły
                    AnimatedVisibility(visible = isExpanded) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate(school.route) },
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = school.buttonColor
                                )
                            ) {
                                Text("Wejdź do szkoły")
                            }
                            // W przyszłości możesz coś dorzucić
                            // np.  drugi guzik np. TextButton("Szybki plan lekcji")
                        }
                    }
                }
            }

        }
    }

}


//@Composable
//fun HomeScreen(navController: NavController){
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(R.drawable.sp_button),
//            contentDescription = "Szkoła podstawowa 311",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .fillMaxWidth(0.7f)
//                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
//                .clip(RoundedCornerShape(40.dp))
//                .clickable { navController.navigate(Screen.Primary.route) }
//        )
//        Image(
//            painter = painterResource(R.drawable.liceum_button),
//            contentDescription = "Liceum XI",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .padding(0.dp ,20.dp ,0.dp ,20.dp)
//                .fillMaxWidth(0.7f)
//                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
//                .clip(RoundedCornerShape(40.dp))
//                .clickable { navController.navigate(Screen.High.route) }
//        )
//        Image(
//            painter = painterResource(R.drawable.technikum_button),
//            contentDescription = "Technikum IX",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .fillMaxWidth(0.7f)
//                .aspectRatio(1.8f)   // dopasuj do proporcji zdjęcia
//                .clip(RoundedCornerShape(40.dp))
//                .clickable { navController.navigate(Screen.Tech.route) }
//        )
//
//
//    }
//}

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
