package com.dhorby.reactive.services


import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.nio.charset.Charset


class WebService {
    fun makeRequest(url: String): Mono<String>? {

        val client = WebClient.create(url)
        val result = client
                .get()
                .accept(MediaType.APPLICATION_RSS_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(String::class.java)

        return result
    }
}

