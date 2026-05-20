package com.example.cyfroweszkoly.data.repository

import com.example.cyfroweszkoly.data.api.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class PaymentRepository {

    suspend fun fetchCleanPaymentInfo(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                // Używamy gotowego klienta API
                val response = NetworkModule.api.getPageBySlug("obiady")

                if (response.isNotEmpty()) {
                    val rawHtml = response.first().content.rendered
                    val cleanText = parseHtmlToCleanText(rawHtml)

                    Result.success(cleanText)
                } else {
                    Result.failure(Exception("Nie znaleziono strony z opłatami."))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun parseHtmlToCleanText(htmlContent: String): String {
        val document = Jsoup.parse(htmlContent)
        document.select("br").append("\\n")
        document.select("p").prepend("\\n")
        document.select("li").prepend("\\n• ")
        return document.text()
            .replace("\\n", "\n")
            .replace("&nbsp;", " ")
            .trim()
    }
}