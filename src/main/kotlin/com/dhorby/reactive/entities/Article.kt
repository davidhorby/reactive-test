package com.dhorby.reactive.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Article{

    @Id
    var id:String?  = null
    var title:String? = null
    var description:String? = null
    var link:String? = null
    var pubDate:String? = null

    constructor() {}

    constructor(id: String, title: String, description: String, link:String, pubDate:String) {
        this.id = id
        this.title = title
        this.description = description
        this.link = link
        this.pubDate = pubDate
    }

    override fun equals(other: Any?): Boolean {
        if(other == null || other !is Article)
            return false
        return title == other.title && description==other.description
    }

    override fun hashCode(): Int =
            (title?.length?.hashCode() ?: 1) * 31+ (description?.length ?: 0)


}