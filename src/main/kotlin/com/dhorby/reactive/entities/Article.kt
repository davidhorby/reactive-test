package com.dhorby.reactive.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Article(@Id val articleId:Integer, val title:String, val description:String)