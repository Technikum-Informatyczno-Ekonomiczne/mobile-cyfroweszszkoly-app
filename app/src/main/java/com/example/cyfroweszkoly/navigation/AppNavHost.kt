package com.example.cyfroweszkoly.navigation


import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyfroweszkoly.ui.about.application.AboutApplicationScreen
import com.example.cyfroweszkoly.ui.about.us.AboutUsScreen
import com.example.cyfroweszkoly.ui.achievements.AchievementsScreen
import com.example.cyfroweszkoly.ui.find_teacher.FindTeacherScreen
import com.example.cyfroweszkoly.ui.history.HistoryScreen
import com.example.cyfroweszkoly.ui.history.HomeScreen
import com.example.cyfroweszkoly.ui.schools.HighSchoolScreen
import com.example.cyfroweszkoly.ui.schools.PrimarySchoolScreen
import com.example.cyfroweszkoly.ui.schools.TechSchoolScreen
import com.example.cyfroweszkoly.ui.theme.CyfroweSzkolyTheme


@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()){

    // NavHost to centrum zarządzania wszystkimi ekranami
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        /*
        composable() to funkcja służąca do rejestrowania pojedynczego ekranu (Composable) pod unikalną nazwą (route)
         */
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(route = Screen.Tech.route){
            TechSchoolScreen(navController)
        }
        
        composable(route = Screen.High.route){
            HighSchoolScreen(navController)
        }

        composable ( route = Screen.Primary.route ){
            PrimarySchoolScreen(navController)
        }

        composable (route = Screen.AboutApplication.route){
            AboutApplicationScreen(navController)
        }

        composable(route= Screen.AboutUs.route) {
            AboutUsScreen(navController)
        }

        composable ( route = Screen.FindTeacher.route ){
            FindTeacherScreen(navController)
        }

        composable(route = Screen.Achievements.route) {
            AchievementsScreen(navController)
        }

        composable(route = Screen.History.route) {
            HistoryScreen(navController)
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppNavHostPreview(){
    val navController = rememberNavController()

    CyfroweSzkolyTheme {
        AppNavHost(navController)
    }
}



