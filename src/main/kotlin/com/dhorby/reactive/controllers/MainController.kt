package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/")
class MainController() {

    @Autowired
    private val repository:ArticleRepository? = null


    @GetMapping("{id}")
    fun getArticle(@PathVariable id: String): Mono<Article> {
        return repository!!.findById(id)
    }

    @GetMapping("/articles")
    fun list(): Flux<Article> {
        return repository!!.findAll()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveArticle(@RequestBody article: Article): Mono<Article> {
        return repository!!.save(article)
    }


}