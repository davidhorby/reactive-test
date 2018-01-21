package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @PutMapping("{id}")
    fun updateArticle(@PathVariable(value = "id") id: String,
                      @RequestBody article: Article): Mono<ResponseEntity<Article>> {
        return repository!!.findById(id)
                .flatMap { existingArticle ->
                    existingArticle.title = article.title
                    existingArticle.description = article.description
                    repository!!.save(existingArticle)
                }
                .map { updateArticle -> ResponseEntity(updateArticle, HttpStatus.OK) }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }


    @DeleteMapping("{id}")
    fun deleteArticle(@PathVariable(value = "id") id: String): Mono<ResponseEntity<Void>> {

        return repository!!.findById(id)
                .flatMap { existingArticle ->
                    repository!!.delete(existingArticle)
                            .then(Mono.just(ResponseEntity<Void>(HttpStatus.OK)))
                }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @DeleteMapping
    fun deleteAllArticles(): Mono<Void> {
        return repository!!.deleteAll()
    }

}