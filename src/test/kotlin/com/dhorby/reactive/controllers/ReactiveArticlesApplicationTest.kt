package com.dhorby.reactive.controllers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveArticlesApplicationTest {

    @Autowired
    lateinit private var webTestClient: WebTestClient

    @Autowired
    lateinit private var repository: ArticleRepository

    val article = Article("22",
            "Article title",
            "description",
            "link",
            "01/01/2015")


    @Test
    fun testCreateOfficer() {

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

    @Test
    fun testGetAllOfficers() {
        webTestClient.get().uri("/articles")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Article::class.java)
    }

    @Test
    fun testGetSingleOfficer() {
        val officer = repository.save(article).block()

        webTestClient.get()
                .uri("/{id}", Collections.singletonMap("id", article.id))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response -> Assertions.assertThat(response.responseBody).isNotNull() }
    }

    @Test
    fun testUpdateArticle() {
        val savedArticle = repository.save(article).block()

        val newArticle = Article("22",
                "New title",
                "New description",
                "New link",
                "12/12/2025")


        webTestClient.put()
                .uri("/{id}", Collections.singletonMap("id", savedArticle.id))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newArticle), Article::class.java)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.title").isEqualTo("New title")
                .jsonPath("$.description").isEqualTo("New description")
                .jsonPath("$.link").isEqualTo("New link")
                .jsonPath("$.pubDate").isEqualTo("12/12/2025")
    }

}