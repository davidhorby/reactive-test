package com.dhorby.reactive

import com.dhorby.reactive.controllers.ArticleHandler
import com.dhorby.reactive.web.Routes
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

@SpringBootApplication
class ReactiveTestApplication

fun main(args: Array<String>) {
//    runApplication<ReactiveTestApplication>(*args)

    val application = SpringApplication(ReactiveTestApplication::class.java)
    application.addInitializers(ApplicationContextInitializer<GenericApplicationContext> { ctx ->
        beans {
            bean<ArticleHandler>()
            bean<Routes>()
        }.initialize(ctx)
    })
    application.run(*args)
}

