package com.ar.maloba.footballnews.model

import org.simpleframework.xml.*

@Root(name = "rss", strict = false)
@Namespace(reference="https://www.telegraph.co.uk")
data class RssResponse (

    @field:Element(name = "version", required = false)
    var version: String? = null,

    @field:Element(name = "channel", required = false)
    var channel : Channel ? = null
)
