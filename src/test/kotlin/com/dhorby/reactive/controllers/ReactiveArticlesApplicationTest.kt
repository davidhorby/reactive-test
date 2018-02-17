package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveArticlesApplicationTest {

    @Autowired
    lateinit private var webTestClient: WebTestClient

    @Autowired
    private val repository: ArticleRepository? = null

    @Test
    fun testCreateOfficer() {
        val article = Article("22", "Article title", "description", "link", "01/01/2015")

        webTestClient.post().uri("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(article), Article::class.java)
                .exchange()
                .expectStatus().isCreated
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.title").isEqualTo("Article title")
                .jsonPath("$.description").isEqualTo("description")
                .jsonPath("$.link").isEqualTo("link")
                .jsonPath("$.pubDate").isEqualTo("01/01/2015")
    }

}