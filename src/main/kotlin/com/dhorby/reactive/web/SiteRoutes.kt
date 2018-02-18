package com.dhorby.reactive.web

import com.dhorby.reactive.controllers.ArticleHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType


class SiteRoutes(private val articleHandler: ArticleHandler) {


    fun router() = org.springframework.web.reactive.function.server.router {
        accept(MediaType.ALL).nest {
            GET("/route/{id}", articleHandler::getArticle)
            GET("/route/articles/", articleHandler::listArticles)
            GET("/cheese", articleHandler::helloWorld)
            POST("/route/", articleHandler::createArticle)
        }
    }
}