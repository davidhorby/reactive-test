package com.dhorby.reactive.web

import com.dhorby.reactive.controllers.ArticleHandler
import org.springframework.context.MessageSource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

class Routes(private val articleHandler: ArticleHandler,
             private val messageSource: MessageSource) {

    fun router() = router {
        accept(APPLICATION_JSON).nest {
            GET("/route/{id}", articleHandler::getArticle)
            GET("/rart", articleHandler::listArticles)
            GET("/helloworld",  articleHandler::helloWorld)
            POST("/route/", articleHandler::createArticle)
        }
    }
}
