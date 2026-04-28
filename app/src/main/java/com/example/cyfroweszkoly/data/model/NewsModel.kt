package com.example.cyfroweszkoly.data.model

import org.simpleframework.xml.Root
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

// Główny tag <rss>
@Root(name = "rss", strict = false)
class RssFeed @JvmOverloads constructor(
    @field:Element(name = "channel")
    var channel: RssChannel? = null
)

// Wewnętrzny tag <channel>, który trzyma listę wiadomości
@Root(name = "channel", strict = false)
class RssChannel @JvmOverloads constructor(
    @field:ElementList(name = "item", inline = true)
    var items: List<RssItem>? = null
)

// Konkretny wpis (nasz pojedynczy News z tytułem, linkiem itp.)
@Root(name = "item", strict = false)
class RssItem @JvmOverloads constructor(
    @field:Element(name = "title", required = false)
    var title: String = "",

    @field:Element(name = "link", required = false)
    var link: String = "",

    @field:Element(name = "pubDate", required = false)
    var pubDate: String = "",

    @field:Element(name = "description", required = false)
    var description: String = ""
)
