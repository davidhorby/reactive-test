package com.dhorby.reactive.dao

import com.dhorby.reactive.entities.Article
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
                .flatMap{ o ->
                    operations!!.createCollection(Article::class.java,
                            CollectionOptions.empty().size((1024 * 1024).toLong()).maxDocuments(100).capped())
                }
                .then()
                .block()

        dao!!.saveAll(Flux.just(Article(Integer(1), "James", "Kirk"),
                Article(Integer(2), "Jean-Luc", "Picard"),
                Article(Integer(3), "Benjamin", "Sisko"),
                Article(Integer(4), "Kathryn", "Janeway"),
                Article(Integer(5), "Jonathan", "Archer")))
                .then()
                .block()

    }

    @Test
    fun testSave() {
        var article = Article(Integer(11), "Nyota", "Uhuru")
        article = dao!!.save(article).block(Duration.ofSeconds(2))
        assertNotNull(Objects.requireNonNull(article).articleId)
        assertEquals(Integer(11), article.articleId)
        assertEquals("Nyota", article.title)
        assertEquals("Uhuru", article.description)
    }
}