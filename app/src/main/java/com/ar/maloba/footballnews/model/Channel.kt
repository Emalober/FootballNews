package com.ar.maloba.footballnews.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root

@NamespaceList(Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom"))
@Root(strict = false)
data class Channel (

    @field:ElementList(entry = "link", inline = true, required = false)
    var links: List<Link>? = null,

    @field:ElementList(name = "item", required = true, inline = true)
    var itemList: List<Item>? = null,

    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "language", required = false)
    var language: String? = null,

    @field:Element(name = "ttl", required = false)
    var ttl: Int = 0,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null

)

@Root(name = "link", strict = false)
data class Link (
    @field:Element(name = "href", required = false)
    var href: String? = null,

    @field:Element(name = "rel", required = false)
    var rel: String? = null,

    @field:Element(name = "contentType", required = false)
    var contentType: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null
)


