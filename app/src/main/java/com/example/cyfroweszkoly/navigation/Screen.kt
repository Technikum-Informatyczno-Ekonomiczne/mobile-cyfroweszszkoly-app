package com.example.cyfroweszkoly.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Primary : Screen("primary")
    object High : Screen("high")
    object Tech : Screen("tech")

    object FindTeacher : Screen("find_teacher")

    object TeacherDetails: Screen("teacher_details/{teacherName}") {
        fun createRoute(teacherName: String) = "teacher_details/$teacherName"
    }
    object Launch: Screen("launch")
    object LaunchPayments: Screen("lunch_payments")
    object News : Screen("news")

    object Chat: Screen(route="chat_screen")

    object History : Screen("history")
    object Achievements : Screen("achievements")
    object AboutUs : Screen("about_us")
    object AboutApplication: Screen("about_application")



    data class Category(val type: String) : Screen("category/$type") {
        companion object {
            const val routeWithArg = "category/{type}"
        }
    }
}