package com.dhorby.reactive.consumers

import com.dhorby.reactive.dao.ArticleRepository
import com.dhorby.reactive.entities.Article
import com.dhorby.reactive.parsers.XMLParser
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Service
class RSSConsumer<String>(repository: ArticleRepository):Consumer<String> {


    val articleConsumer: (MutableList<Article>) -> Unit  = { articles ->
        articles.forEach { article ->
            repository.save(article)
                    .doOnError { error -> println("Error saving article " + error.message) }
                    .subscribe()
        }
    }

    val errorConsumer: (Throwable) -> Unit = { error -> println(error.message) }

    companion object {
        val xmlParser = XMLParser()
    }

    override fun accept(t: String) {
        val articleList = xmlParser.parseRss(t.toString())
        Flux.just(articleList).subscribe(articleConsumer, errorConsumer)
    }

}