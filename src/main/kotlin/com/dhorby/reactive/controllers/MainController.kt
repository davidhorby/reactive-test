package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/")
class MainController() {

    @Autowired
    private val repository:ArticleRepository? = null

    @GetMapping("{id}")
    fun getOfficer(@PathVariable id: Integer): Mono<Article> {
        return repository!!.findById(id)
    }


}