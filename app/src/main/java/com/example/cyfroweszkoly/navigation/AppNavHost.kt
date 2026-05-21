package com.example.cyfroweszkoly.navigation


import androidx.compose.foundation.layout.Column
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cyfroweszkoly.ui.about.application.AboutApplicationScreen
import com.example.cyfroweszkoly.ui.about.us.AboutUsScreen
import com.example.cyfroweszkoly.ui.achievements.AchievementsScreen
import com.example.cyfroweszkoly.ui.chat.ChatScreen
import com.example.cyfroweszkoly.ui.find_teacher.FindTeacherScreen
import com.example.cyfroweszkoly.ui.history.HistoryScreen
import com.example.cyfroweszkoly.ui.history.HomeScreen
import com.example.cyfroweszkoly.ui.launch.LunchScreen
import com.example.cyfroweszkoly.ui.launch.PaymentInfoScreen
import com.example.cyfroweszkoly.ui.news.NewsScreen
import com.example.cyfroweszkoly.ui.schools.HighSchoolScreen
import com.example.cyfroweszkoly.ui.schools.PrimarySchoolScreen
import com.example.cyfroweszkoly.ui.schools.TechSchoolScreen
import com.example.cyfroweszkoly.ui.teacher_details_screen.TeacherDetailsScreen
import com.example.cyfroweszkoly.viewmodel.ChatViewModel
import com.example.cyfroweszkoly.viewmodel.LunchViewModel
import com.example.cyfroweszkoly.viewmodel.PaymentViewModel
import com.example.cyfroweszkoly.viewmodel.TeacherViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
){
    val teacherViewModel: TeacherViewModel = viewModel()
    val lunchViewModel: LunchViewModel = viewModel()
    val paymentViewModel: PaymentViewModel = viewModel()
    val chatViewModel: ChatViewModel = viewModel()

    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.weight(1f)
        ) {
            /*
        composable() to funkcja służąca do rejestrowania pojedynczego ekranu (Composable) pod unikalną nazwą (route)
         */
            composable(route = Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(route = Screen.Tech.route) {
                TechSchoolScreen(navController)
            }

            composable(route = Screen.High.route) {
                HighSchoolScreen(navController)
            }

            composable(route = Screen.Chat.route) {
                ChatScreen(
                    viewModel = chatViewModel,
                    onNavigateBack = {navController.popBackStack()}
                    )
            }

            composable(route = Screen.Primary.route) {
                PrimarySchoolScreen(navController)
            }

            composable(route = Screen.AboutApplication.route) {
                AboutApplicationScreen(navController)
            }

            composable(route = Screen.AboutUs.route) {
                AboutUsScreen(navController)
            }

            composable(route = Screen.FindTeacher.route) {

                FindTeacherScreen(
                    viewModel = teacherViewModel,
                    onTeacherClick = { teacher ->

                        navController.navigate(Screen.TeacherDetails.createRoute(teacher.id))

                        println("kliknięto: ${teacher.name}")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.TeacherDetails.route,
                arguments = listOf(navArgument("teacherId") {
                    type = NavType.IntType
                }) // Mówimy, że spodziewamy się liczby całkowitej (Int)
            ) { backStackEntry ->
                // Wyciągamy ID z linku (jeśli z jakiegoś powodu go nie ma, używamy 0)
                val teacherId = backStackEntry.arguments?.getInt("teacherId") ?: 0

                TeacherDetailsScreen(
                    viewModel = teacherViewModel,
                    teacherId = teacherId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable ( route = Screen.Launch.route ){
                LunchScreen(
                    viewModel =  lunchViewModel,
                    onNavigateToPayments = {
                        navController.navigate("lunch_payments")
                    }
                )
            }

            composable(route = Screen.LaunchPayments.route) {
                PaymentInfoScreen(
                    viewModel = paymentViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.News.route,) {
                NewsScreen(navController)
            }


            composable(route = Screen.Achievements.route) {
                AchievementsScreen(navController)
            }

            composable(route = Screen.History.route) {
                HistoryScreen(navController)
            }



        }

    }
}






