package com.dhorby.reactive

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ArticleInit(private val operations: ReactiveMongoOperations, private val dao: ArticleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        operations.collectionExists(Article::class)
                .flatMap{ exists -> if (exists) operations.dropCollection(Article::class) else Mono.just(exists!!) }
                .flatMap{ o ->
                    operations.createCollection(Article::class.java,
                            CollectionOptions.empty().size((1024 * 1024).toLong()).maxDocuments(100).capped())
                }
                .then()
                .block()

        dao.saveAll(Flux.just(Article("1", "James", "Kirk"),
                Article("2", "Jean-Luc", "Picard"),
                Article("3", "Benjamin", "Sisko"),
                Article("4", "Kathryn", "Janeway"),
                Article("5", "Jonathan", "Archer")))
                .then()
                .block()    }
}