package com.example.cyfroweszkoly

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
// test
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


    // wysyłamy powiadomieni na kanał "global_alerts",
    // trafi ono natychmiast do wszystkich, którzy mają aplikację.
    FirebaseMessaging.getInstance().subscribeToTopic("global_alerts")
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Pomyślnie zapisano do nasłuchu alertów!")
            } else {
                Log.e("FCM", "Błąd zapisu do alertów",
                    task.exception)
            }
        }


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

//    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//        if (!task.isSuccessful) {
//            Log.e("FCM", "Pobieranie tokenu nie powiodło się", task.exception)
//            return@addOnCompleteListener
//        }
//        // Zdobyliśmy token! Wypisujemy go na czerwono w Logcacie, żeby łatwo go znaleźć
//        Log.e("FCM_TOKEN", "TWÓJ TOKEN TO: ${task.result}")
//    }
    }
}


