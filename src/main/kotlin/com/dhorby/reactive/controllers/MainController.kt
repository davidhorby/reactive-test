package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import com.dhorby.reactive.services.WebService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/")
class MainController {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    val metricConsumer: (String) -> Unit = { message ->  println(message) }

    @Autowired
    lateinit var repository:ArticleRepository;

    @GetMapping("beans")
    fun listBean(): List<String> {
        val beanDefinitionNames: List<String> = applicationContext.getBeanDefinitionNames().asList()
        return beanDefinitionNames
    }

    @GetMapping("/process")
    fun processArticles():String {
        val rrsUrl: String = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml?edition=uk#"
        WebService(repository).makeRequest(rrsUrl)
        return "complete"
    }


    @GetMapping("{id}")
    fun getArticle(@PathVariable id: String): Mono<Article> {
        return repository.findById(id)
    }

    @GetMapping("/articles")
    fun list(): Flux<Article> {
        return repository.findAll()
    }

    @GetMapping("/save")
    fun save():Mono<Article> {
        return repository.save(Article("4343", "Hello", "World", "link", "01/01/2015"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveArticle(@RequestBody article: Article): Mono<Article> {
        return repository.save(article)
    }

    @PutMapping("{id}")
    fun updateArticle(@PathVariable(value = "id") id: String,
                      @RequestBody article: Article): Mono<ResponseEntity<Article>> {
        return repository.findById(id)
                .flatMap { existingArticle ->
                    existingArticle.title = article.title
                    existingArticle.description = article.description
                    existingArticle.link = article.link
                    existingArticle.pubDate = article.pubDate
                    repository.save(existingArticle)
                }
                .map { updateArticle -> ResponseEntity(updateArticle, HttpStatus.OK) }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }


    @DeleteMapping("{id}")
    fun deleteArticle(@PathVariable(value = "id") id: String): Mono<ResponseEntity<Void>> {

        return repository.findById(id)
                .flatMap { existingArticle ->
                    repository.delete(existingArticle)
                            .then(Mono.just(ResponseEntity<Void>(HttpStatus.OK)))
                }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @DeleteMapping
    fun deleteAllArticles(): Mono<Void> {
        return repository.deleteAll()
    }

}