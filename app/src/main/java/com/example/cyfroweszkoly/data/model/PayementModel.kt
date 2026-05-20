package com.example.cyfroweszkoly.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentModel(
    @SerialName("id") val id: Int,
    @SerialName("slug") val slug: String,
    @SerialName("title") val title: RenderedText,
    @SerialName("content") val content: RenderedText
)

@Serializable
data class RenderedText(
    @SerialName("rendered") val rendered: String
)