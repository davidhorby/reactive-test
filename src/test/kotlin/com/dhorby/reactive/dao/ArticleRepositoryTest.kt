package com.dhorby.reactive.dao

import com.dhorby.reactive.entities.Article
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*
import java.util.stream.Collectors

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner::class)
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private val dao: ArticleRepository? = null

    @Autowired
    private val operations: ReactiveMongoOperations? = null

    @Before
    fun setUp() {
        operations!!.collectionExists(Article::class)
                .flatMap{ exists -> if (exists) operations!!.dropCollection(Article::class) else Mono.just(exists!!) }
                .flatMap{
                    operations!!.createCollection(Article::class.java,
                            CollectionOptions.empty().size((1024 * 1024).toLong()).maxDocuments(100).capped())
                }
                .then()
                .block()

        dao!!.saveAll(Flux.just(Article("1", "Title 1", "Desc 1"),
                Article("2", "Title 2", "Desc 2"),
                Article("3", "Title 3", "Desc 3"),
                Article("4", "Title 4", "Desc 4"),
                Article("5", "Title 5", "Desc 5")))
                .then()
                .block()

    }

    @Test
    fun testSave() {
        var article = Article("11", "No news today", "Nothing happened")
        article = dao!!.save(article).block(Duration.ofSeconds(2))
        assertNotNull(Objects.requireNonNull(article).id)
        assertEquals("11", article.id)
        assertEquals("No news today", article.title)
        assertEquals("Nothing happened", article.description)
    }

    @Test
    fun testUpdate() {
        var article = Article("22", "Article title", "Article title")
        article = dao!!.save(article).block(Duration.ofSeconds(2))
        assertNotNull(Objects.requireNonNull(article).id)
        article.title = "New title"
        article.description = "New description"
        article = dao!!.save(article).block(Duration.ofSeconds(2))
        assertEquals("22", article.id)
        assertEquals("New title", article.title)
        assertEquals("New description", article.description)
    }

    @Test
    fun findAll() {
        val dbNames = dao!!.findAll()
                .map { article -> article.description }
                .collect(Collectors.toList()).block()
        assertThat(dbNames, containsInAnyOrder<String>("Desc 1", "Desc 2", "Desc 3", "Desc 4", "Desc 5"))
    }

}