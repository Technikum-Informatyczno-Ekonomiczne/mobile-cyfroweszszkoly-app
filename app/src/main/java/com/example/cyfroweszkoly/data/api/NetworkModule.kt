package com.example.cyfroweszkoly.data.api



import com.example.cyfroweszkoly.data.model.PaymentModel
import com.example.cyfroweszkoly.data.model.WpPost
import kotlinx.serialization.json.Json

import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApiService {
    @GET("wp-json/wp/v2/posts?_embed")
    suspend fun getNewsFeed(): List<WpPost>


    //Endpoint do pobierania konkretnej strony po jej slugu
    @GET("wp-json/wp/v2/pages")
    suspend fun getPageBySlug(@Query("slug") slug: String): List<PaymentModel>
}
object NetworkModule {

    // Tworzymy konfigurator JSON, który mówi:
    // "Ignoruj wszystko, czego nie zapisałem w NewsModel.kt"
    private val networkJson = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cyfroweszkoly.pl/")
        // Podpinamy nasz natywny konwerter
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: SchoolApiService by lazy {
        retrofit.create(SchoolApiService::class.java)
    }
}

