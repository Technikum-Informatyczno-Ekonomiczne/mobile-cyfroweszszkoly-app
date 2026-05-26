package com.example.cyfroweszkoly.ui.lunch

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cyfroweszkoly.data.model.DailyMenu
import com.example.cyfroweszkoly.data.model.MealCourse

class LunchViewModel : ViewModel() {

    // Lista obserwowana przez UI
    // (ten sam wzorzec co w  alertach)
    var weeklyMenu = mutableStateListOf<DailyMenu>()
        private set

    init {
        loadHardcodedData()
    }

    private fun loadHardcodedData() {
        val mockData = listOf(
            DailyMenu(
                day = "Poniedziałek",
                date = "11.05.2026",
                courses = listOf(
                    MealCourse("Zupa", "Jarzynowa z ziemniakami", "300ml", "7/9"),
                    MealCourse("II danie", "Spaghetti z sosem bolońskim, ser", "350g", "1/3/7/9"),
                    MealCourse("Przekąska", "Jabłko", "150g", ""),
                    MealCourse("Napój", "Kompot z porzeczki", "200ml", "")
                )
            ),
            DailyMenu(
                day = "Wtorek",
                date = "12.05.2026",
                courses = listOf(
                    MealCourse("Zupa", "Szpinakowa z jajkiem", "300ml", "3/7/9"),
                    MealCourse("II danie", "Kotlet mielony, ziemniaki", "350g", "1/3/9"),
                    MealCourse("Surówka", "Buraczki z cebulką", "100g", ""),
                    MealCourse("Napój", "Kompot truskawkowy", "200ml", "")
                )
            ),
            DailyMenu(
                day = "Środa",
                date = "13.05.2026",
                courses = listOf(
                    MealCourse("Zupa", "Rosół drobiowy z makaronem", "300ml", "1/3/9"),
                    MealCourse("II danie", "Udziec pieczony, ziemniaki", "350g", "9"),
                    MealCourse("Surówka", "Mizeria ze śmietaną", "100g", "7"),
                    MealCourse("Napój", "Kompot wieloowocowy", "200ml", "")
                )
            ),
            DailyMenu(
                day = "Czwartek",
                date = "14.05.2026",
                courses = listOf(
                    MealCourse("Zupa", "Z fasolki szparagowej z ziemniakami", "300ml", "6/7/9"),
                    MealCourse("II danie", "Gulasz z szynki, kasza gryczana", "350g", "1/9"),
                    MealCourse("Surówka", "Ogórek konserwowy", "100g", ""),
                    MealCourse("Napój", "Kompot z wiśni", "200ml", "")
                )
            ),
            DailyMenu(
                day = "Piątek",
                date = "15.05.2026",
                courses = listOf(
                    MealCourse("Zupa", "Zupa krem z brokuła", "300ml", "7/9"),
                    MealCourse("II danie", "Ryba w panierce, ryż z warzywami", "350g", "1/4"),
                    MealCourse("Deser", "Kisiel cytrynowy", "200ml", "")
                )
            )
        )

        weeklyMenu.clear()
        weeklyMenu.addAll(mockData)
    }
}