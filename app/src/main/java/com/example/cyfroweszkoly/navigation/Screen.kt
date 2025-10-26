package com.example.cyfroweszkoly.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Primary : Screen("primary")
    object High : Screen("high")
    object Tech : Screen("tech")
    object History : Screen("history")
    object Achievements : Screen("achievements")

    data class Category(val type: String) : Screen("category/$type") {
        companion object {
            const val routeWithArg = "category/{type}"
        }
    }
}