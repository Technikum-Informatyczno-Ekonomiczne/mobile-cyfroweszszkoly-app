package com.example.cyfroweszkoly.data.repository

import com.example.cyfroweszkoly.data.api.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class PaymentRepository {

    suspend fun fetchCleanPaymentInfo(): Result<Pair<String, String>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.api.getPageBySlug("obiady")

                if (response.isNotEmpty()) {
                    val rawHtml = response.first().content.rendered
                    val fullCleanText = parseHtmlToCleanText(rawHtml)

                    // Odcinamy wszystko od słowa "UWAGA!" w dół
                    val dynamicText = fullCleanText.substringBefore("UWAGA!").trim()

                    // Wyciągamy linijkę z telefonem
                    val lines = dynamicText.lines()
                    val contactInfo = lines.firstOrNull { it.contains("Intendent", ignoreCase = true) } ?: ""

                    //  Reszta to cenniki
                    val pricingInfo = dynamicText.replace(contactInfo, "").trim()

                    Result.success(Pair(contactInfo, pricingInfo))
                } else {
                    Result.failure(Exception("Nie znaleziono strony z opłatami."))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun parseHtmlToCleanText(htmlContent: String): String {
        val document = org.jsoup.Jsoup.parse(htmlContent)
        document.select("br").append("\\n")
        // Dodajemy podwójny znak nowej linii dla wyraźnego oddzielenia SP i LO
        document.select("p").prepend("\\n\\n")
        document.select("li").prepend("\\n• ")

        val fullText = document.text()
            .replace("\\n", "\n")
            .replace("&nbsp;", " ")
            .trim()

        // Usuwamy ewentualne zbyt duże odstępy (redukujemy >2 pustych linii do max 2)
        return fullText.replace(Regex("\n{3,}"), "\n\n")
    }
}