package com.example.cyfroweszkoly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.navigation.AppNavHost
import com.example.cyfroweszkoly.navigation.Screen
import com.example.cyfroweszkoly.ui.main.MainScreen
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
          METAFORY
            - Theme = styl Twojego pokoju
            - scope = pracownik, który faktycznie otwiera drzwi, gdy dasz mu sygnał
             */
            CyfroweSzkolyTheme {
                MainScreen()

            }
        }
    }
}

        /*
         //  obiekt, który zarządza przechodzeniem między ekranami

                val navController = rememberNavController()



                val drawerState = rememberDrawerState(DrawerValue.Closed)



                val scope = rememberCoroutineScope()


                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Text(
                                text = "Menu główne",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )
                            NavigationDrawerItem(
                                label = {Text("Strona główna")},
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        navController.navigate(Screen.Home.route)
                                    }
                                }
                            )

                           NavigationDrawerItem(
                               label = {Text("O aplikacji")},
                               selected = false,
                               onClick = {
                                   scope.launch {
                                       drawerState.close()
                                       navController.navigate(Screen.AboutApplication.route)
                                   }
                               }
                           )

                           NavigationDrawerItem(
                               label = {Text("O autorach")},
                               selected = false,
                               onClick = {
                                   scope.launch {
                                       drawerState.close()
                                       navController.navigate(Screen.AboutUs.route)
                                   }
                               }
                           )

                        }
                    }
                ) {

                    // 🔳 Główny układ aplikacji
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Cyfrowe Szkoły") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu"
                                        )
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->

                        // 👇 przekazujemy PaddingValues dalej do NavHosta
                        AppNavHost(
                            navController = navController,
                            innerPadding = innerPadding
                        )
                    }
                }
         */