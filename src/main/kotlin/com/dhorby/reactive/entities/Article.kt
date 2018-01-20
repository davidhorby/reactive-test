package com.dhorby.reactive.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Article{


    @Id
    var id:String?  = null
    var title:String? = null
    var description:String? = null

    constructor() {}

    constructor(id: String, first: String, last: String) {
        this.id = id
        this.title = first
        this.description = last
    }

}