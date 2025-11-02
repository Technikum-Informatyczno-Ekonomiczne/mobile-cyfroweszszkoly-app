package com.example.cyfroweszkoly.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
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
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.navigation.AppNavHost
import kotlinx.coroutines.launch

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
                    title = { Text("Cyfrowe Szkoły")},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
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