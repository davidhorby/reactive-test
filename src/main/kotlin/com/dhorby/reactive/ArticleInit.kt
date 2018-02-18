package com.dhorby.reactive

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ArticleInit(private val operations: ReactiveMongoOperations, private val dao: ArticleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Initialising collections")
        operations.collectionExists(Article::class)
                .flatMap{ exists -> if (exists) operations.dropCollection(Article::class) else Mono.just(exists!!) }
                .flatMap{ o ->
                    operations.createCollection(Article::class.java,
                            CollectionOptions.empty().size((1024 * 1024).toLong()).maxDocuments(1000))
                }
                .then()
                .block()
        println("Initialisation complete")
//        dao.saveAll(Flux.just(Article("1", "James", "Kirk", "link", "01/01/2015"),
//                Article("2", "Jean-Luc", "Picard",  "link","01/01/2015"),
//                Article("3", "Benjamin", "Sisko", "link","01/01/2015"),
//                Article("4", "Kathryn", "Janeway",  "link","01/01/2015"),
//                Article("5", "Jonathan", "Archer",  "link","01/01/2015")))
//                .then()
//                .block()
 }
}