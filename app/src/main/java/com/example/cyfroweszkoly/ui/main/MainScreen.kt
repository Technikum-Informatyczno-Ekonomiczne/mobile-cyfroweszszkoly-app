package com.example.cyfroweszkoly.ui.main

import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


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
                        Text(
                            text = "Cyfrowe Szkoły",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                        )
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
                            //navController.navigate(Screen.FindTeacher.route)
                        }){
                            Icon(
                                imageVector = Icons.Outlined.PersonSearch,
                                contentDescription = "Znajdź nauczyciela"
                            )
                        }

//                        IconButton(onClick={
//                            navController.navigate(Screen.Home.route)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Outlined.Home,
//                                contentDescription = "Strona główna"
//                            )
//                        }

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