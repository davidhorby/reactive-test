package com.dhorby.reactive

import com.dhorby.reactive.services.ArticleService

object TestApp {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Started")
        val articleService = ArticleService()
        articleService.retrieveArticles("https://feeds.bbci.co.uk", "/news/rss.xml")
        println("Finished")
    }

}