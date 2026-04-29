package com.example.cyfroweszkoly.data.model


import androidx.core.text.HtmlCompat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WpPost(
    val id: Int,
    val date: String,
    val link: String,
    val title: WpRendered,
    val excerpt: WpRendered,

    @SerialName("_embedded") val embedded: WpEmbedded? = null
) {
    val cleanTitle: String
        get() = HtmlCompat.fromHtml(title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    val cleanExcerpt: String
        get() = HtmlCompat.fromHtml(excerpt.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()

    val imageUrl: String?
        get() = embedded?.wpFeaturedMedia?.firstOrNull()?.sourceUrl
}

@Serializable
data class WpRendered(
    val rendered: String
)

@Serializable
data class WpEmbedded(
    @SerialName("wp:featuredmedia") val wpFeaturedMedia: List<WpMedia>? = null
)

@Serializable
data class WpMedia(
    @SerialName("source_url") val sourceUrl: String
)



//
//import org.simpleframework.xml.Root
//import org.simpleframework.xml.Element
//import org.simpleframework.xml.ElementList
//
//// Główny tag <rss>
//@Root(name = "rss", strict = false)
//class RssFeed @JvmOverloads constructor(
//    @field:Element(name = "channel")
//    var channel: RssChannel? = null
//)
//
//// Wewnętrzny tag <channel>, który trzyma listę wiadomości
//@Root(name = "channel", strict = false)
//class RssChannel @JvmOverloads constructor(
//    @field:ElementList(name = "item", inline = true)
//    var items: List<RssItem>? = null
//)
//
//// Konkretny wpis (nasz pojedynczy News z tytułem, linkiem itp.)
//@Root(name = "item", strict = false)
//class RssItem @JvmOverloads constructor(
//    @field:Element(name = "title", required = false)
//    var title: String = "",
//
//    @field:Element(name = "link", required = false)
//    var link: String = "",
//
//    @field:Element(name = "pubDate", required = false)
//    var pubDate: String = "",
//
//    @field:Element(name = "description", required = false)
//    var description: String = ""
//)
