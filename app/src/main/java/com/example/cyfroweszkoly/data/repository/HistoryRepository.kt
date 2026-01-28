package com.example.cyfroweszkoly.data.repository

import android.content.Context
import com.example.cyfroweszkoly.models.HistoryItem
import org.json.JSONArray
import org.json.JSONObject

/**
 * Repository odpowiedzialne za wczytywanie historii szkoły
 * z pliku JSON przy użyciu org.json (wbudowane w Androida).
 *
 * Rozwiązanie nie wymaga żadnych dodatkowych zależności.
 */
class HistoryRepository (private val context: Context) {


    fun loadHistory(): List<HistoryItem>{

        // 1. Odczyt pliku JSON z assets
        val json = context.assets
            .open("daneDoTestu.json")
            .bufferedReader()
            .use { it.readText() }

        // 2. Parsowanie danych Json do tablicy

        val jsonArray = JSONArray(json)

        val historyList = mutableListOf<HistoryItem>()

        //3. Reczne mapowanie obiektow

        for (i in 0 until jsonArray.length()){
            val jsonObject : JSONObject = jsonArray.getJSONObject(i)

            val item = HistoryItem(
                date = jsonObject.getString("date"),
                title = jsonObject.getString("title"),
                description = jsonObject.getString("description"),
                image = if (jsonObject.has("image") && !jsonObject.isNull("image")) {
                    jsonObject.getString("image")
                } else {
                    null
                }
            )
            historyList.add(item)

        }

        return historyList

    }

}