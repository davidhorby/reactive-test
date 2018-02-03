package com.dhorby.reactive.services


import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import java.net.URL
import java.util.concurrent.CompletableFuture






class ArticleService {

    fun retrieveArticles(url: String, uri: String) {
        // http://feeds.bbci.co.uk/news/rss.xml
        val bbcTopStories = WebClient.create(url).method(HttpMethod.GET).uri(uri)
        val response2: String = bbcTopStories.exchange()
                .block()
                .bodyToMono(String::class.java)
                .block()

        val completableFuture = CompletableFuture
                .supplyAsync {
                    //starts a background thread the ForkJoin common pool
                    readRSS(URL("http://feeds.bbci.co.uk/news/rss.xml"))
                }
//        val observable = Single.from(completableFuture)

    }

    fun readRSS(feedUrl: URL): Array<Any?> {
        val input: SyndFeedInput =  SyndFeedInput()
        val feed = input.build(XmlReader(feedUrl))
        return feed.entries.toTypedArray()
    }
}