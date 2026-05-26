package com.example.cyfroweszkoly.navigation

import com.example.cyfroweszkoly.ui.search.SearchType

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Primary : Screen("primary")
    object High : Screen("high")
    object Tech : Screen("tech")

    object GlobalSearch : Screen("global_search")

    object ScheduleDetails: Screen("schedule_details/{query}/{type}"){
        fun createRoute(query: String, type: SearchType): String = "schedule_details/$query/${type.name}"
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