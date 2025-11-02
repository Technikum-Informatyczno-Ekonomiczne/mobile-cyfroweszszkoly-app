package com.example.cyfroweszkoly.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Primary : Screen("primary")
    object High : Screen("high")
    object Tech : Screen("tech")
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