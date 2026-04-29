package com.example.cyfroweszkoly.data.api



import com.example.cyfroweszkoly.data.model.WpPost
import kotlinx.serialization.json.Json

import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

interface NewsApiService {
    @GET("wp-json/wp/v2/posts?_embed")
    suspend fun getNewsFeed(): List<WpPost>
}

object NetworkModule {

    // 1. Tworzymy konfigurator JSON, który mówi: "Ignoruj wszystko, czego nie zapisałem w NewsModel.kt"
    private val networkJson = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cyfroweszkoly.pl/")
        // 2. Podpinamy nasz natywny konwerter
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}



//
//import com.example.cyfroweszkoly.data.model.RssFeed
//import retrofit2.Retrofit
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory
//import retrofit2.http.GET
//
//// Definiujemy, JAKIE dane chcemy pobrać z adresu URL
//interface NewsApiService {
//    // Bazowy adres to będzie cyfroweszkoly.pl,
//    // a tu podajemy końcówkę ścieżki
//    @GET("archiwa/category/aktualnosci/feed")
//    suspend fun getNewsFeed(): RssFeed
//}
//
//// Tworzymy gotowego do użycia klienta Retrofit
//object NetworkModule {
//
//    @Suppress("DEPRECATION")
//    private val retrofit = Retrofit.Builder()
//        // Adres bazowy ZAWSZE musi kończyć się ukośnikiem (slash)
//        .baseUrl("https://www.cyfroweszkoly.pl/")
//        // Dodajemy nasz tłumacz XML
//        .addConverterFactory(SimpleXmlConverterFactory.create())
//        .build()
//
//    // To jest zmienna, której będziemy używać w aplikacji do pobierania danych
//    val api: NewsApiService by lazy {
//        retrofit.create(NewsApiService::class.java)
//    }
//}