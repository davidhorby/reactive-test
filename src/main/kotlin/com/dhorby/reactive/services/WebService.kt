package com.dhorby.reactive.services


import com.dhorby.reactive.consumers.RSSConsumer
import com.dhorby.reactive.dao.ArticleRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.nio.charset.Charset


class WebService(val repository: ArticleRepository) {
    fun makeRequest(url: String) {

        WebClient.create(url)
                .get()
                .accept(MediaType.APPLICATION_RSS_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(String::class.java)
                .subscribe(RSSConsumer(repository))
    }
}

