package com.example.cyfroweszkoly.ui.main

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.navigation.AppNavHost
import com.example.cyfroweszkoly.navigation.Screen
import kotlinx.coroutines.launch

import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){


    //- navController = plan domu (wiadomo, do którego pokoju idziemy)
    // to obiekt do zarządzania przechodzeniem między ekranami
    /*
        Przechowuje stos ekranów (back stack) i obsługuje navigate(), popBackStack() itd.
        Funkcja rememberNavController() tworzy go raz i zapamiętuje,
        żeby nie tworzył się ponownie przy każdym odświeżeniu UI (recomposition).
     */
    val navController = rememberNavController()


    // drawerState = drzwi, które można otworzyć/zamknąć
    /*  To stan bocznego menu (`Drawer`) — mówi Compose, czy menu jest otwarte, czy zamknięte.
        `DrawerValue.Closed` oznacza, że startowo `Drawer` jest schowany.
        `rememberDrawerState` zapamiętuje jego stan między odświeżeniami UI.
        Dzięki temu, jeśli klikniesz menu, ono się otworzy i nie zniknie przy każdej zmianie w UI.
     */
    val drawerState = rememberDrawerState(DrawerValue.Closed)


    /*
      To jest „pamiętany” zakres korutyny Compose — czyli miejsce,
      w którym możesz uruchamiać akcje asynchroniczne, np. otwieranie/zamykanie Drawer’a
    */
    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onNavigate = { route ->
                    scope.launch {
                        drawerState.close()
                        navController.navigate(route)
                    }
                }
            )
        }
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Black,
                        navigationIconContentColor = Color.Black

                    ),
                    title = {
                        // Pakujemy ikonę i tekst w Row, który jest klikalny
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                                .background(Color.LightGray.copy(alpha = 0.3f))
                                .padding(vertical = 3.dp, horizontal = 4.dp)
                        ) {
                            // Mała, czarna ikonka reprezentująca szkołę
                            Icon(
                                imageVector = Icons.Outlined.School,
                                contentDescription = null, // decorative
                                tint = Color.Black
                            )

                            // Odstęp między ikonką a tekstem
                            Spacer(modifier = Modifier.width(6.dp))

                            // Twój wystylizowany tekst
                            Text(
                                text = "Cyfrowe Szkoły",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                ),
                                color = Color.Black // Upewnij się, że kolor jest czarny
                            )
                        }
                    },


                    // to jest ikona nawigacji (trzy poziome kreski)
                    // po kliknięciu otwiera się boczne menu
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "Menu")
                        }
                    },

                    // tu są zdefiniowane ikonki wyświetlane po prawej stronie

                    actions = {

                        IconButton(onClick = {
                            navController.navigate(Screen.FindTeacher.route)
                        }){
                            Icon(
                                imageVector = Icons.Outlined.PersonSearch,
                                contentDescription = "Znajdź nauczyciela"
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate(Screen.History.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.History,
                                contentDescription = "Historia szkoły"
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate(Screen.Achievements.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.EmojiEvents,
                                contentDescription = "Osiągnięcia"
                            )
                        }

                    }

                )
            },
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    // 1. Przycisk START (Powiązany ze Screen.Home)
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Start") },
                        label = { Text("Start") },
                        selected = currentRoute == Screen.Home.route, // Tu sprawdzamy czy trasa to "home"
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    // 2. Przycisk AKTUALNOŚCI (Powiązany ze Screen.News)
                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Aktualności") },
                        label = { Text("Aktualności") },
                        selected = currentRoute == Screen.News.route, // Tu sprawdzamy czy trasa to "news"
                        onClick = {
                            navController.navigate(Screen.News.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    MainScreen()
}