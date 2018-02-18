package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ArticleHandler {

    private val  repository: ArticleRepository

    constructor(repository: ArticleRepository) {
        this.repository = repository
    }

    fun helloWorld(request: ServerRequest): Mono<ServerResponse> {
        val words: Flux<String> = Flux.just("Hello", "World")
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(words, String::class.java)
    }

    fun listArticles(request: ServerRequest): Mono<ServerResponse> {
        val articles = repository.findAll()
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(articles, Article::class.java)
    }

    fun createArticle(request: ServerRequest): Mono<ServerResponse> {
        val articleMono = request.bodyToMono(Article::class.java)
        return articleMono.flatMap({ article ->
            ServerResponse.status(HttpStatus.CREATED)
                    .contentType(APPLICATION_JSON)
                    .body(repository.save(article), Article::class.java)
        })
    }

    fun getArticle(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val notFound = ServerResponse.notFound().build()
        val personMono = this.repository.findById(id)
        return personMono
                .flatMap({ person ->
                    ServerResponse.ok()
                            .contentType(APPLICATION_JSON)
                            .body(fromObject(person))
                })
                .switchIfEmpty(notFound)
    }
}