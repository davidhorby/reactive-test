package com.dhorby.reactive

import com.dhorby.reactive.controllers.ArticleHandler
import com.dhorby.reactive.web.Routes
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ReactiveTestApplication {

    @Bean
    fun router(articleHandler: ArticleHandler) = router {
        "/route".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/{id}", articleHandler::getArticle)
                GET("/", articleHandler::listArticles)
                GET("/hello", articleHandler::helloWorld)
                POST("/", articleHandler::createArticle)
            }
        }
    }

}



fun main(args: Array<String>) {
//    runApplication<ReactiveTestApplication>(*args)
//SpringApplication.run(Application::class.java, *args)
    val application = SpringApplication(ReactiveTestApplication::class.java)
    application.addInitializers(ApplicationContextInitializer<GenericApplicationContext> { ctx ->
        beans {
            bean<ArticleHandler>()
            bean<Routes>()
        }.initialize(ctx)
    })
    application.run(*args)
}


