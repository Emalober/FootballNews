package com.ar.maloba.footballnews.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item (

    @field:Element(name = "title", required = false)
    var title: String? = null,//The title of the item.	Venice Film Festival Tries to Quit Sinking

    @field:Element(name = "link", required = false)
    var link: String? = null,//The URL of the item.	http://www.nytimes.com/2002/09/07/movies/07FEST.html

    @field:Element(name = "description", required = false)
    var description: String? = null,//The item synopsis.	Some of the most heated chatter at the Venice Film Festival this week was about the way that the arrival of the stars at the Palazzo del Cinema was being staged.

    @field:Element(name = "author", required = false)
    var author: String? = null,//Email address of the author of the item. More.	oprah@oxygen.net

    @field:ElementList(name = "category", inline = true, required = false)
    var categories: List<String>? = null,//Includes the item in one or more categories. More.	Simpsons Characters @field:Element var comments: String? = null,//URL of a page for comments relating to the item. More.	http://www.myblog.org/cgi-local/mt/mt-comments.cgi?entry_id=290

    @field:Element(name = "enclosure", required = false)
    var enclosure: Enclosure? = null,//	Describes a media object that is attached to the item. More.	<enclosure url="http://live.curry.com/mp3/celebritySCms.mp3" length="1069871" type="audio/mpeg"/> @field:Element var guid: String? = null,//A string that uniquely identifies the item. More.	<guid isPermaLink="true">http://inessential.com/2002/09/01.php#a2</guid>

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,//	Indicates when the item was published. More.	Sun, 19 May 2002 15:21:36 GMT

    @field:Element(name = "source", required = false)
    var source: String? = null//	The RSS channel that the item came from. More.

)

data class Enclosure (
    @field:Attribute(name="url")
    var url: String? = null,

    @field:Attribute(name="length")
    var length: Int? = null,

    @field:Attribute(name="type")
    var type: String? = null
)
